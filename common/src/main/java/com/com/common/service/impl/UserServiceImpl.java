package com.com.common.service.impl;

import com.com.common.dto.UserSaveDto;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.User;
import com.com.common.repository.UserRepository;
import com.com.common.service.EmailService;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Value("${site.url}")
    private String siteUrl;

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
    public void sendVerificationEmail(User user) throws MessagingException {
        UUID uuid = UUID.randomUUID();
        user.setToken(uuid.toString());
        userRepository.save(user);
        String verifyUrl = siteUrl + "/verifyEmail?email="+user.getEmail()+"&token="+uuid;
//        emailService.send(user.getEmail(), "Verify acc",
//                "HI "+ user.getName()+", \n" +
//                String.format("please click on %s ", verifyUrl));
        emailService.sendHtmlEmail(user.getEmail(), "Verify your account in OnlineShop", user, verifyUrl, "verifyTemplate");

    }

    @Override
    public void verifyUser(String email, String token) throws UserNotFoundException {
        Optional<User> byEmail = findUserByEmail(email);
//        if (byEmail.isPresent()) {
//            User user = byEmail.get();
//            if (user.getToken().equals(UUID.fromString(token))) {
//                user.setActiveProfile(true);
//                user.setToken(null);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getToken().equals(token)) {
                user.setActiveProfile(true);
                user.setToken("");
                userRepository.save(user);
            }
        }

    }

    @Override
    public void addUser(User user, Locale locale) {
        userRepository.save(user);
        try {
            sendVerificationEmail(user);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
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
