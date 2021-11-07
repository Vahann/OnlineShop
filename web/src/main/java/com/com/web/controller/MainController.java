package com.com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "index";
    }
    @GetMapping("/index.html")
    public String index(){
        return "index";
    }
    @GetMapping("/cart.html")
    public String feature(){
        return "cart";
    }
    @GetMapping("/product.html")
    public String sale(){
        return "cart";
    }
    @GetMapping("/blog.html")
    public String blog(){
        return "blog";
    }
    @GetMapping("/about.html")
    public String about(){
        return "about";
    }
    @GetMapping("/contact.html")
    public String contact(){
        return "contact";
    }
}
