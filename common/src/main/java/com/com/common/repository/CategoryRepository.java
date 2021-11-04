package com.com.common.repository;

import com.com.common.model.Category;
import com.com.common.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

//    List<Category> findProductByCategoryName(String categoryName);
    List<Category> findCategoryByName(String name);


//    List<Product> findProductByCategory(String categoryName);
//
//    @Override
//    List<Product> findProductByCategoryName(String categoryName);
}
