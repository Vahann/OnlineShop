package com.com.rest.endpoint;


import com.com.common.dto.UserDto;
import com.com.common.dto.UserSaveDto;
import com.com.common.model.User;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
    public List<UserDto> getAllUser() {
        List<User> allUser=userService.findAllUsers();
      List<UserDto> userDtos=new ArrayList<>();
        for (User user:allUser) {
            UserDto userDto=mapper.map(user,UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
//        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) {

        Optional<User> userById = userService.findUserById(id);
        if (userById.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(mapper.map(userById.get(),UserDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserSaveDto> deleteUserById(@PathVariable("id") int id) {
        if (userService.changeStatusUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserSaveDto userSaveDto){

//        if (userService.findUserByEmail(userSaveDto.getEmail()).isEmpty()){
        if (userService.findUserByEmail(userSaveDto.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//            return ResponseEntity.notFound().build();
        }
        userService.addUser(mapper.map(userSaveDto,User.class));
    return ResponseEntity.ok(mapper.map(userSaveDto,UserDto.class));
    }



}
