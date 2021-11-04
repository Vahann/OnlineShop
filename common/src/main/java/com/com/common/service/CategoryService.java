package com.com.common.service;

import com.com.common.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findCategoryByName(String name);

    List<Category> findAll();

}
