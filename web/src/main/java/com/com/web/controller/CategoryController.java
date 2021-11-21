package com.com.web.controller;


import com.com.common.dto.response.CategoryResponse;
import com.com.common.exception.CategoryNotFoundException;
import com.com.common.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String  getAllCategories(ModelMap modelMap) throws CategoryNotFoundException {
//        log.info("user {} call method get all product", currentService.currentUser().getEmail());
        List<CategoryResponse> categories=categoryService.convertCategory(categoryService.findAllCategory());
        modelMap.addAttribute("categories", categories);
        return "categories";
    }
}
