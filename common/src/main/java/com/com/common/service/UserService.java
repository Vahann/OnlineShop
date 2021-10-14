package com.com.common.service;

import com.com.common.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();

    Optional<User> findUserById(int id);

    boolean changeStatusUser(int id);

    Optional<User> findUserByEmail(String email);

    void addUser(User user);
}
