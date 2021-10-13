package com.com.rest.endpoint;


import com.com.common.model.User;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {


    private final UserService userService;

    @GetMapping("/")
    public List<User> getAllUser() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {

        Optional<User> userById = userService.findUserById(id);
        if (userById.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(userById.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") int id) {
        if (userService.changeStatusUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
