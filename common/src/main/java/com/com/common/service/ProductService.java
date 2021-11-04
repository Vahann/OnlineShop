package com.com.common.service;

import com.com.common.exception.ProductNotFoundException;
import com.com.common.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product findProductById(int id) throws ProductNotFoundException;

    boolean nullifyProduct(int id) throws ProductNotFoundException;

    Product addProduct(Product product);

    Product updateProduct(int id, Product product) throws ProductNotFoundException;

    List<Product> findProductByCategoryName(String categoryName);


    List<Product> findProductByProductName(String productName);

    List<Product> getProductByProductForGender(String gender);

    List<Product> findProductByPriceBetween(double startPrice,double endPrice);

    List<Product> findProductByPrice(String price);

    List<Product> findProductBySize(String size);

    List<Product> filterForProduct(String productName, String method);
}
