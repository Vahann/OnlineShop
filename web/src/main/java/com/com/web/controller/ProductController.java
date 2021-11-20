package com.com.web.controller;

import com.com.common.dto.response.ProductResponse;
import com.com.common.exception.ProductNotFoundException;
import com.com.common.repository.ProductRepository;
import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("/products")
    public String  getAllProducts(ModelMap modelMap) {
//        log.info("user {} call method get all product", currentService.currentUser().getEmail());
        List<ProductResponse> products=productService.convertProduct(productService.findAllProducts());
        modelMap.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/delete")
    public String edit(@RequestParam("id") int id) throws ProductNotFoundException {
        productService.nullifyProduct(id);
        return "redirect:/products";
    }
}
