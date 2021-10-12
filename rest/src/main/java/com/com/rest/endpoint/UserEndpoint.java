package com.com.rest.endpoint;


import com.com.common.model.User;
import com.com.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {

    //
    private final UserRepository userRepository;

    @GetMapping("/")
    public List<User> getAllUser() {
        //service
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        //service
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(userById.get());
    }

  ///new test
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") int id) {
        //service
       Optional<User> userById=userRepository.findById(id);
        if (userById.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        User user=userById.get();
        user.setActiveProfile(false);
        userRepository.save(user);
//        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
