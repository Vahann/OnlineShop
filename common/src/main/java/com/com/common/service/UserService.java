package com.com.common.service;

import com.com.common.dto.UserSaveDto;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
//
    List<User> findAllUsers();
//
    User findUserById(int id) throws UserNotFoundException;
//
    Optional<User> findUserByEmail(String email) throws UserNotFoundException;
//
    boolean changeStatusUser(String email) throws UserNotFoundException;
//
    Optional<User> checkUserByEmail(String email);

/**
 //OK
    List<User> findAllUsers();
*/
/**
    Optional<User> findUserById(int id);
*/
/**
    boolean changeStatusUser(int id);
 */
/**
    Optional<User> findUserByEmail(String email);
*/
    void addUser(User user);
//    Optional<User> addUser(User user);

    User updateUser(UserSaveDto userSaveDto) throws UserNotFoundException;
 //OK
// */
}
