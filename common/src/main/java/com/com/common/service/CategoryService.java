package com.com.common.service;

import com.com.common.dto.response.CategoryResponse;
import com.com.common.dto.response.ProductResponse;
import com.com.common.exception.CategoryNotFoundException;
import com.com.common.model.Category;
import com.com.common.model.Product;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category findCategoryByName(String name) throws CategoryNotFoundException;

    List<Category> findAllCategory();

    Optional<Category> checkCategoryByName(String name);

    Category addCategory(Category category);

    List<CategoryResponse> convertCategory(List<Category> categoryList);
}
