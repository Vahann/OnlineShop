package com.com.common.repository;

import com.com.common.model.Product;
import com.com.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
