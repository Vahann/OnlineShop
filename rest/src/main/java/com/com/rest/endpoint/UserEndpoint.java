package com.com.rest.endpoint;

import com.com.common.model.User;
import com.com.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

//
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepository.findAll();

    }

}
