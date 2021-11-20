package com.com.common.service.impl;

import com.com.common.dto.response.CategoryResponse;
import com.com.common.dto.response.ProductResponse;
import com.com.common.exception.CategoryNotFoundException;
import com.com.common.model.Category;
import com.com.common.repository.CategoryRepository;
import com.com.common.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public Category findCategoryByName(String name) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findCategoryByName(name);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        return category.get();
    }

    @Override
    public List<Category> findAllCategory() {
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

    @Override
    public List<CategoryResponse> convertCategory(List<Category> categoryList) {
        List<CategoryResponse> categoryDtos = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponse categoryDto = mapper.map(category, CategoryResponse.class);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }



}
