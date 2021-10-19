package com.com.common.service.impl;

import com.com.common.dto.UserSaveDto;
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

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean changeStatusUser(int id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            User user = userById.get();
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
        userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(int id, UserSaveDto userSaveDto) {
        Optional<User> userBiId = userRepository.findById(id);
        if (userBiId.isEmpty()) {
            return Optional.empty();
        }
        User userUpdate = userBiId.get();
        userUpdate.setName(userSaveDto.getName());
        userUpdate.setSurname(userSaveDto.getSurname());
        userUpdate.setEmail(userSaveDto.getEmail());
        userUpdate.setPhoneNumber(userSaveDto.getPhoneNumber());
        userUpdate.setGender(userSaveDto.getGender());
        userUpdate.setAge(userSaveDto.getAge());
        userUpdate.setRole(userSaveDto.getRole());

        userRepository.save(userUpdate);

        return userRepository.findById(id);
//        return userBiId;
    }
}
