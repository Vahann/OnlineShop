package com.com.rest.endpoint;


import com.com.common.dto.UserAuthDto;
import com.com.common.dto.UserAuthResponseDto;
import com.com.common.dto.UserDto;
import com.com.common.dto.UserSaveDto;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.User;
import com.com.common.service.UserService;
import com.com.rest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {


    private final UserService userService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/")
    public List<UserDto> getAllUser() {
        List<User> allUser = userService.findAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : allUser) {
            UserDto userDto = mapper.map(user, UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    //
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) throws UserNotFoundException {
        return ResponseEntity.ok(mapper.map(userService.findUserById(id), UserDto.class));
    }

    //
    @DeleteMapping("/{id}")
    public ResponseEntity<UserSaveDto> deleteUserById(@PathVariable("id") int id) throws UserNotFoundException {
        if (userService.changeStatusUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserAuthDto userAuthDto) throws UserNotFoundException {

        Optional<User> userByEmail = userService.checkUserByEmail(userAuthDto.getEmail());
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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserSaveDto userSaveDto) {

        if (userService.checkUserByEmail(userSaveDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
            userService.addUser(mapper.map(userSaveDto, User.class));
        }
        return ResponseEntity.ok(mapper.map(userSaveDto, UserDto.class));
    }

    @PutMapping("/update")
    // except password
    public ResponseEntity<UserDto> updateUserById(@RequestBody UserSaveDto userSaveDto) throws UserNotFoundException {
        User user = mapper.map(userService.updateUser(userSaveDto), User.class);

        return ResponseEntity.ok(mapper.map(user, UserDto.class));
    }
}
