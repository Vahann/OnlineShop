package com.com.common.service.impl;

import com.com.common.dto.UserSaveDto;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.User;
import com.com.common.repository.UserRepository;
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

    //
    @Override
    public User findUserById(int id) throws UserNotFoundException {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }
        return userById.get();
    }

    //
    @Override
    public Optional<User> findUserByEmail(String email) throws UserNotFoundException {
        Optional<User> userById = userRepository.findByEmail(email);
        if (userById.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }

        return userRepository.findByEmail(email);
    }

    ////    @Override
////    public User findUserByEmail(String email) throws UserNotFoundException {
////       Optional<User> userByEmail=userRepository.findByEmail(email);
////       if (userByEmail.isEmpty()){
////           throw  new UserNotFoundException();
////       }
////       return userByEmail.get();
////    }
//
    @Override
    public boolean changeStatusUser(String email) throws UserNotFoundException {
        User user = findUserByEmail(email).get();
        user.setActiveProfile(!user.isActiveProfile());
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> checkUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User updateUser(UserSaveDto userSaveDto) throws UserNotFoundException {
        Optional<User> userByEmail = findUserByEmail(userSaveDto.getEmail());
        if (userByEmail.isEmpty()) {
            throw new UserNotFoundException();
        }
        User userUpdate = userByEmail.get();

        userUpdate.setName(userSaveDto.getName());
        userUpdate.setSurname(userSaveDto.getSurname());
        userUpdate.setEmail(userSaveDto.getEmail());
        userUpdate.setPhoneNumber(userSaveDto.getPhoneNumber());
        userUpdate.setGender(userSaveDto.getGender());
//        userUpdate.setActiveProfile(userSaveDto.isActive());
        userUpdate.setAge(userSaveDto.getAge());
        userUpdate.setRole(userSaveDto.getRole());

        return userRepository.save(userUpdate);
    }
}
