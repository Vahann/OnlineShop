package com.com.rest.endpoint;


import com.com.common.dto.request.UserAuthRequest;
import com.com.common.dto.response.UserAuthResponse;
import com.com.common.dto.response.UserResponse;
import com.com.common.dto.request.UserRequest;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.User;
import com.com.common.service.UserService;
import com.com.rest.security.CurrentUserDetailsServiceImpl;
import com.com.rest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {


    private final UserService userService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final CurrentUserDetailsServiceImpl currentService;

    @GetMapping("/")
    public List<UserResponse> getAllUser() throws UserNotFoundException {
        List<User> allUser = userService.findAllUsers();
        List<UserResponse> userDtos = new ArrayList<>();
        for (User user : allUser) {
            UserResponse userDto = mapper.map(user, UserResponse.class);
            userDtos.add(userDto);
        }
        log.info("user {} call method get all users", currentService.currentUser().getEmail());
        return userDtos;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") int id) throws UserNotFoundException {
        log.info("User {} searching User by id {}", currentService.currentUser().getEmail(), id);
        return ResponseEntity.ok(mapper.map(userService.findUserById(id), UserResponse.class));
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<UserRequest> deleteUserByEmail(@PathVariable("email") String email) throws UserNotFoundException {
        if (userService.changeStatusUser(email)) {
            log.info("User {} deleted User by id {}", currentService.currentUser().getEmail(), userService.findUserByEmail(email).get());

            return ResponseEntity.noContent().build();
        }
        log.warn("User {} tried to change status User by id ", currentService.currentUser().getEmail());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody @Valid UserAuthRequest userAuthDto) throws UserNotFoundException {

        Optional<User> userByEmail = userService.checkUserByEmail(userAuthDto.getEmail());
        if (userByEmail.isEmpty()) {

            log.info("user tries to log in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userByEmail.get();
        if (passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())) {
            log.info("user {} successfully logged in", userAuthDto.getEmail());
            return ResponseEntity.ok(
                    UserAuthResponse.builder()
                            .token(jwtTokenUtil.generateToken(user.getEmail()))
                            .userResponse(mapper.map(user, UserResponse.class))
                            .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/add")
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest userSaveDto, Locale locale) throws MessagingException {

        if (userService.checkUserByEmail(userSaveDto.getEmail()).isPresent()) {
            log.warn("unsuccessful addition user");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
            userService.addUser(mapper.map(userSaveDto, User.class), locale);
            log.info("successful addition of user {}", userSaveDto.getEmail());
//            emailService.send(userSaveDto.getEmail(), "welcome", "dear  "+userSaveDto.getName()+
//                    ", you successfuly registreted"  );

        }
        return ResponseEntity.ok(mapper.map(userSaveDto, UserResponse.class));
    }

    @PutMapping("/update")
    // except password
    public ResponseEntity<UserResponse> updateUserById(@RequestBody @Valid UserRequest userSaveDto) throws UserNotFoundException {
        User user = mapper.map(userService.updateUser(userSaveDto), User.class);
        log.info("the CurrentUser {} successfully updated the data of another user {}",
                currentService.currentUser().getEmail(), user);
        return ResponseEntity.ok(mapper.map(user, UserResponse.class));
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity verifyEmail(@RequestParam("email") String email,
                                      @RequestParam("token") String token) throws UserNotFoundException {
        userService.verifyUser(email, token);
        log.info("User {} verified", currentService.currentUser().getEmail());
        // userRepository.save();
        return ResponseEntity.ok().build();
    }
}
