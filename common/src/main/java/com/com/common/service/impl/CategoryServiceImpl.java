package com.com.common.service.impl;

import com.com.common.model.Category;
import com.com.common.model.Product;
import com.com.common.repository.CategoryRepository;
import com.com.common.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

//    @Override
//    public List<Category> findProductByCategoryName(String categoryName) {
//        return categoryRepository.findProductByCategoryName(categoryName);
//    }


//    @Override
//    public List<Product> findProductByCategoryName(String categoryName) {
//        return categoryRepository.findProductByCategory(categoryName);
//    }
}
