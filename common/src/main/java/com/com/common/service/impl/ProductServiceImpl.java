package com.com.common.service.impl;

import com.com.common.model.Product;
import com.com.common.repository.ProductRepository;
import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findProductById(int id) {

        return productRepository.findById(id);
    }

    @Override
    public void addProduct(Product product) {  //MultipartFile multipartFile

        productRepository.save(product);
    }

//    @Override
//    public void updateProduct(Product product) {
//
//    }



    @Override
    public boolean nullifyProduct(int id) {    //deleteProduct
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isPresent()) {
            Product product = productById.get();
            product.setCount(0);
            productRepository.save(product);
            return true;
        }
        return false;
    }

//    @Override
//    public Optional<Product> findSaleByProductId(int id) {
//
//
//        return Optional.empty();
//    }
}
