package com.com.rest.security;

import com.com.common.model.User;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrentUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.checkUserByEmail(email); //.orElse(null)
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CurrentUser(user.get());
    }
//        User user = null;
//        try {
//            user = userService.findUserByEmail(email);//.orElse(null);
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//        }
////        if (user == null) {
////            throw new UsernameNotFoundException("Username not found");
////        }
//        return new CurrentUser(user);
//
//    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> user = userService.findUserByEmail(email);
//        if (user.isEmpty()) {
//            throw new UsernameNotFoundException("Username not found");
//        }
//        return new CurrentUser(user.get());
//    }
}
