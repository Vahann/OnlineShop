package com.com.web.controller;

import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
//@AllArgsConstructor

public class ProductController {

    private final ProductService productService;

    @RequestMapping("/1")
    public String main(){

        return"prod";
    }


//
//    @GetMapping("/1")
//    public String product(ModelMap modelMap){
//        List<Product> productList=productService.findAllProducts();
//        modelMap.addAttribute("list",productList);
//        return "productPage";
//    }

}
