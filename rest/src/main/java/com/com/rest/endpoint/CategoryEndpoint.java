package com.com.rest.endpoint;


import com.com.common.model.Category;
import com.com.common.model.Product;
import com.com.common.service.CategoryService;
import com.com.rest.security.CurrentUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryEndpoint {


    private final CurrentUserDetailsServiceImpl currentService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public List<Category> searchAllCategory(){
        return categoryService.findAll();
    }

    @GetMapping("/{categoryName}")
    public List<Category> searchByCategoryName(@PathVariable("categoryName")String categoryName){
        List<Category> categories=categoryService.findCategoryByName(categoryName);
        return categories;
    }

}
