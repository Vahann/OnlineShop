package com.com.rest.endpoint;


import com.com.common.dto.UserAuthDto;
import com.com.common.dto.UserAuthResponseDto;
import com.com.common.dto.UserDto;
import com.com.common.dto.UserSaveDto;
import com.com.common.model.User;
import com.com.common.service.UserService;
import com.com.rest.security.CurrentUser;
import com.com.rest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserEndpoint {


    private final UserService userService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;


    @GetMapping("/")
    public List<UserDto> getAllUser(@AuthenticationPrincipal CurrentUser currentUser) {
        List<User> allUser = userService.findAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : allUser) {
            UserDto userDto = mapper.map(user, UserDto.class);
            userDtos.add(userDto);
        }
        log.info("User with {} opened getAllUser page, allUsers{}",
                currentUser.getUser().getEmail(), allUser.size());
        return userDtos;
//        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {

        Optional<User> userById = userService.findUserById(id);
        if (userById.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        log.info("user {} searched by ID", currentUser.getUser().getEmail() );
        return ResponseEntity.ok(mapper.map(userById.get(), UserDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserSaveDto> deleteUserById(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (userService.changeStatusUser(id)) {
            log.info("user {} changed the status of another user {}", currentUser.getUser().getEmail(),
                    userService.findUserById(id)); // kareli e sarqel emailov
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserAuthDto userAuthDto, @AuthenticationPrincipal CurrentUser currentUser) {

        Optional<User> userByEmail = userService.findUserByEmail(userAuthDto.getEmail());
        if (userByEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userByEmail.get();
        if (passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(
                    UserAuthResponseDto.builder()
                            .token(jwtTokenUtil.generateToken(user.getEmail()))
                            .userDto(mapper.map(user, UserDto.class))
                            .build());
        }
        log.info("user {} logged in ", currentUser.getUser().getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserSaveDto userSaveDto) {

        if (userService.findUserByEmail(userSaveDto.getEmail()).isPresent()) {
            log.info("an error occurred while adding a new user HttpStatus.CONFLICT ");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//            return ResponseEntity.notFound().build();
        } else {
            userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
            userService.addUser(mapper.map(userSaveDto, User.class));
        }
        log.info("a new user has been added");
        return ResponseEntity.ok(mapper.map(userSaveDto, UserDto.class));
    }

    @PutMapping("/update/{id}")
    // except password
    public ResponseEntity<UserDto> updateUserById(@PathVariable("id") int id,
                                                  @RequestBody UserSaveDto userSaveDto,@AuthenticationPrincipal CurrentUser currentUser) {

        if ((userService.updateUser(id, userSaveDto)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
            log.info("user data updated {}",currentUser.getUser());
        return ResponseEntity.ok(mapper.map(userSaveDto, UserDto.class));
    }

}
