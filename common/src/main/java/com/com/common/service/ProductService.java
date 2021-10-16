package com.com.common.service;

import com.com.common.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProducts();

    Optional<Product> findProductById(int id);

    //    void updateProduct(Product product);
//
    void addProduct(Product product, MultipartFile multipartFile);    // MultipartFile multipartFile

    boolean nullifyProduct(int id);

//    Optional<Product> findSaleByProductId(int id);


}
