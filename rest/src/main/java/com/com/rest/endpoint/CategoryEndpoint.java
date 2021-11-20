package com.com.rest.endpoint;


import com.com.common.dto.request.CategoryRequest;
import com.com.common.dto.response.CategoryResponse;
import com.com.common.exception.CategoryNotFoundException;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.Category;
import com.com.common.service.CategoryService;
import com.com.rest.security.CurrentUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryEndpoint {


    private final CurrentUserDetailsServiceImpl currentService;
    private final CategoryService categoryService;
    private final ModelMapper mapper;

    @GetMapping("/")
    public List<CategoryResponse> searchAllCategory() throws UserNotFoundException {

//        log.info("user {} call method get all category", currentService.currentUser().getEmail());
//        return categoryService.convertCategory(categoryService.findAllCategory());

        // convertCategory need change

        List<Category> categoryList=categoryService.findAllCategory();
        List<CategoryResponse> categoryResponseList=new ArrayList<>();
        for (Category category:categoryList){
            CategoryResponse categoryResponse=mapper.map(category,CategoryResponse.class);
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryResponse> searchByCategoryName(@PathVariable("categoryName")
                                                                         String categoryName) throws CategoryNotFoundException, UserNotFoundException {
        log.info("user {} searching category by name {}", currentService.currentUser().getEmail(), categoryName);
        return ResponseEntity.ok(mapper.map(categoryService.findCategoryByName(categoryName), CategoryResponse.class));
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) throws UserNotFoundException {
        if (categoryService.checkCategoryByName(categoryRequest.getName()).isPresent()) {
            log.warn("unsuccessful adding category");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Category category = categoryService.addCategory(mapper.map(categoryRequest, Category.class));
        log.info("user {} to add a category{} ", currentService.currentUser().getEmail(), categoryRequest);
        return ResponseEntity.ok(mapper.map(category, CategoryResponse.class));
    }


}
