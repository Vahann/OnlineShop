package com.com.common.service.impl;

import com.com.common.exception.ProductNotFoundException;
import com.com.common.model.Product;
import com.com.common.repository.ProductRepository;
import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(int id) throws ProductNotFoundException {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new ProductNotFoundException("Product does not exist");
        }
        return productById.get();
    }

    @Override
    public boolean nullifyProduct(int id) throws ProductNotFoundException {
        Product productById = findProductById(id);
        productById.setCount(0);
        productRepository.save(productById);
        return true;
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int id, Product product) throws ProductNotFoundException {

        Product productUpdate = findProductById(id);

        productUpdate.setProductName(product.getProductName());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setCount(product.getCount());
        productUpdate.setProductForGender(product.getProductForGender());
        productUpdate.setSize(product.getSize());
        // pic
        productUpdate.setPicUrl(product.getPicUrl());

        return productRepository.save(productUpdate);
    }
}
