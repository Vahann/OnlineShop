package com.com.common.service;

import com.com.common.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findById(int id);

    void deleteById(int id);

}
