package com.com.common.service.impl;

import com.com.common.model.Product;
import com.com.common.model.User;
import com.com.common.repository.ProductRepository;
import com.com.common.repository.UserRepository;
import com.com.common.service.ProductService;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            user.setActiveProfile(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
