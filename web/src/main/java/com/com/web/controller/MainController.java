package com.com.web.controller;

import com.com.common.model.User;
import com.com.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class MainController {

    private final UserRepository userRepository;

    @GetMapping("/1")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/seller")
    public String userPage(ModelMap modelMap) {
        List<User> users = userRepository.findAll();
        modelMap.addAttribute("users", users);
        return "seller";
    }


//    @GetMapping("/successLogin")
//    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
//        if (currentUser == null) {
//            return "redirect:/";
//        }
//        User user = currentUser.getUser();
//        if (user.getRole() == Role.ADMIN) {
//            return "redirect:/admin";
//        } else {
//            return "redirect:/user";
//        }
//    }
}