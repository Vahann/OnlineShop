package com.com.rest.endpoint;


import com.com.common.exception.ProductNotFoundException;
import com.com.common.model.Product;
import com.com.common.service.ProductService;
import com.com.rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {


    private final ProductService productService;

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id, @AuthenticationPrincipal
                                    CurrentUser currentUser) throws ProductNotFoundException {
        log.info("User {} searching product by id ", currentUser.getUser().getEmail());
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") int id, @AuthenticationPrincipal
                                     CurrentUser currentUser) throws ProductNotFoundException {
        log.info("User {} deleted product by id {} ", currentUser.getUser().getEmail(), productService.findProductById(id).getProductName());
        productService.nullifyProduct(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProducts(@RequestBody Product product,@AuthenticationPrincipal
                                                 CurrentUser currentUser) {
        if (product == null) {
            log.info("user {} tried to add a product ", currentUser.getUser().getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        log.info("user {} to add a product{} ", currentUser.getUser().getEmail(),product);
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PostMapping("/addImage/{productId}")
    public ResponseEntity addImage(@PathVariable("productId") int productId ,@AuthenticationPrincipal
            CurrentUser currentUser, @RequestParam(value = "image") MultipartFile multipartFile) throws IOException, ProductNotFoundException {

        Product productById = productService.findProductById(productId);

        String pic = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadDir + File.separator + pic));

        productById.setPicUrl(pic);
        productService.addProduct(productById);
        log.info("user {} to add image a product {} ", currentUser.getUser().getEmail(),productById);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/openImage")
    public void openImage(@RequestParam("fileName") String fileName,
                          HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        InputStream in = new FileInputStream(uploadDir + File.separator + fileName);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") int id,
                                                     @RequestBody Product product,@AuthenticationPrincipal
                                                     CurrentUser currentUser) throws ProductNotFoundException {
        log.info("user {} to update a product{} ", currentUser.getUser().getEmail(),product);
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }
}
