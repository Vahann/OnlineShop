package com.com.rest.endpoint;


import com.com.common.exception.CategoryNotFoundException;
import com.com.common.model.Category;
import com.com.common.service.CategoryService;
import com.com.rest.security.CurrentUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public List<Category> searchAllCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<Category> searchByCategoryName(@PathVariable("categoryName")
                                                                 String categoryName) throws CategoryNotFoundException {

        return ResponseEntity.ok(categoryService.findCategoryByName(categoryName));
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        if (categoryService.checkCategoryByName(category.getName()).isPresent()) {
            log.warn("unsuccessful adding category");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(categoryService.addCategory(category));
    }


}
