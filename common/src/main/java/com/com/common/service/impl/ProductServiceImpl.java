package com.com.common.service.impl;

import com.com.common.dto.response.ProductResponse;
import com.com.common.exception.ProductNotFoundException;
import com.com.common.model.Product;
import com.com.common.model.enums.ProductForGender;
import com.com.common.model.enums.Size;
import com.com.common.repository.ProductRepository;
import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

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
        //change Category

        return productRepository.save(productUpdate);
    }

    @Override
    public List<Product> findProductByCategoryName(String categoryName) {
        return productRepository.findProductByCategoryName(categoryName);
    }

    @Override
    public List<Product> findProductByProductName(String productName) {
        return productRepository.findProductByProductName(productName);
    }

    @Override
    public List<Product> getProductByProductForGender(String gender) {

        return productRepository.findProductByProductForGender(ProductForGender.valueOf(gender));
    }

    @Override
    public List<Product> findProductByPriceBetween(double startPrice, double endPrice) {
        return productRepository.findProductByPriceBetween(startPrice, endPrice);
    }

    @Override
    public List<Product> findProductByPrice(double price) {
        return productRepository.findProductProductByPrice(price);
    }


    @Override
    public List<Product> findProductBySize(String size) {
        return productRepository.findProductBySize(Size.valueOf(size));
    }

    @Override
    public List<Product> filterForProduct(String variable, String method) {

        List<Product> products = new ArrayList<>();
        try {
            if (method.equals("gender")) {
                products = productRepository.findProductByProductForGender(
                        ProductForGender.valueOf(variable.toUpperCase()));
            }
            if (method.equals("size")) {

                products = productRepository.findProductBySize(
                        Size.valueOf(variable.toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        }
        if (method.equals("category")) {
            products = productRepository.findProductByCategoryName(variable);
        }
        if (method.equals("name")) {
            products = productRepository.findProductByProductName(variable);
        }

        return products;
    }

    @Override
    public List<ProductResponse> convertProduct(List<Product> productList) {

        List<ProductResponse> productDtos = new ArrayList<>();
        for (Product product : productList) {
            ProductResponse productDto = mapper.map(product, ProductResponse.class);
            productDtos.add(productDto);
        }
        return productDtos;
    }


}
