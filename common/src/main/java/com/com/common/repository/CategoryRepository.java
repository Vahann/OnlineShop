package com.com.common.repository;

import com.com.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    Optional<Category> findCategoryByName(String name);


}
