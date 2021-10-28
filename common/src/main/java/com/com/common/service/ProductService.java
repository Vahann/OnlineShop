package com.com.common.service;

import com.com.common.exception.ProductNotFoundException;
import com.com.common.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    Product findProductById(int id) throws ProductNotFoundException;

    boolean nullifyProduct(int id) throws ProductNotFoundException;

    Product addProduct(Product product);

    Product updateProduct(int id, Product product) throws ProductNotFoundException;


//Ok 
 /**
    List<Product> findAllProducts();

    Optional<Product> findProductById(int id);

    void addProduct(Product product);    // MultipartFile multipartFile

    boolean nullifyProduct(int id);

    Product updateProduct(int id, Product product) throws ProductNotFoundException;
    */
//   OK 


}
