package com.com.common.service;

import com.com.common.exception.CategoryNotFoundException;
import com.com.common.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category findCategoryByName(String name) throws CategoryNotFoundException;

    List<Category> findAll();

    Optional<Category> checkCategoryByName(String name);

    Category addCategory(Category category);
}
