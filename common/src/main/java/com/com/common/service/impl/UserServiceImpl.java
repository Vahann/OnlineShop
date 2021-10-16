package com.com.common.service.impl;

import com.com.common.model.User;
import com.com.common.repository.UserRepository;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean changeStatusUser(int id) {
        Optional<User> userById=userRepository.findById(id);
        if (userById.isPresent()){
            User user=userById.get();
            user.setActiveProfile(!user.isActiveProfile());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void addUser(User user) {
        try {
            if (findUserByEmail(user.getEmail()).isEmpty()){
                userRepository.save(user);
            }
        }catch (Exception e){
            System.out.println("user with this email is already registered");
        }

        // return userRepository.save(user);
    }
}
