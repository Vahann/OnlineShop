package com.com.web.controller;

import com.com.common.exception.ProductNotFoundException;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.User;
import com.com.common.repository.UserRepository;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SellerController {

    private final UserService userService;

    @GetMapping("/seller")
    public String userPage(ModelMap modelMap) {
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("users", users);
        return "seller";
    }

    @GetMapping("/seller/delete")
    public String edit(@RequestParam("email") String email) throws UserNotFoundException {
        userService.changeStatusUser(email);
        return "redirect:/seller";
    }
    @GetMapping("/sellerFind")
    public String searchByEmail(@RequestParam("email") String email, ModelMap modelMap) throws UserNotFoundException {
       User users = userService.findUserByEmail(email).get();
        modelMap.addAttribute("users", users);
        return "singleUser";
    }

}
