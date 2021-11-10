package com.com.common.service.impl;

import com.com.common.exception.CategoryNotFoundException;
import com.com.common.model.Category;
import com.com.common.repository.CategoryRepository;
import com.com.common.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryByName(String name) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findCategoryByName(name);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        return category.get();
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> checkCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

//    @Override
//    public List<Category> findProductByCategoryName(String categoryName) {
//        return categoryRepository.findProductByCategoryName(categoryName);
//    }

}
