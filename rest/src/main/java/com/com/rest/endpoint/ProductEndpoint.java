package com.com.rest.endpoint;


import com.com.common.model.Product;
import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    //
    private final ProductService productService;
//    private final ProductRepository productRepository;

//    @Value("${upload.dir}")
    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @PostMapping("/addImage/{productId}")
    public ResponseEntity addImage(@PathVariable("productId") int productId, @RequestParam(value = "image") MultipartFile multipartFile) throws IOException {

        Optional<Product> byId = productService.findProductById(productId);
        if (byId.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        String pic = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadDir + File.separator + pic));
        Product product = byId.get();
        product.setPicUrl(pic);
//        productRepository.save(product);
        productService.addProduct(product);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> productById = productService.findProductById(id);
        if (productById.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(productById.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") int id) {
        if (productService.nullifyProduct(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public void addProducts(@RequestBody Product product) { //multipartFile

        productService.addProduct(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") int id,
                                                     @RequestBody Product product) {
       try {
           Product productUpdate = productService.updateProduct(id, product);
           return ResponseEntity.ok(productUpdate);
       }catch (NullPointerException e){
           return ResponseEntity.notFound().build();
       }


    }
}
