package com.com.rest.endpoint;


import com.com.common.model.Product;
import com.com.common.service.ProductService;
import com.com.rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/openImage")
    public void openImage(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        InputStream in = new FileInputStream(uploadDir + File.separator + fileName);
        IOUtils.copy(in, response.getOutputStream());

    }

    @PostMapping("/addImage/{productId}")
    public ResponseEntity addImage(@PathVariable("productId") int productId,
                                   @RequestParam(value = "image") MultipartFile multipartFile,
                                   @AuthenticationPrincipal CurrentUser currentUser) throws IOException {

        Optional<Product> byId = productService.findProductById(productId);
        if (byId.isEmpty()) {
            log.info("the user {} tried to add a picture to a non-existent product", currentUser.getUser().getEmail());
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
        log.info("the user {} added a picture to the product", currentUser.getUser().getEmail());
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
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") int id,
                                                     @AuthenticationPrincipal CurrentUser currentUser) {
        if (productService.nullifyProduct(id)) {
            log.info("user {} deleted the product", currentUser.getUser().getEmail());
            return ResponseEntity.noContent().build();

        }
        log.info("user {} deleted the product ", currentUser.getUser().getEmail());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public void addProducts(@RequestBody Product product, @AuthenticationPrincipal CurrentUser currentUser) { //multipartFile

        productService.addProduct(product);
        log.info("user {} added a product ", currentUser.getUser().getEmail());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") int id,
                                                     @RequestBody Product product,
                                                     @AuthenticationPrincipal CurrentUser currentUser) {
       try {
           Product productUpdate = productService.updateProduct(id, product);
           log.info("user {} updated the product ", currentUser.getUser().getEmail());
           return ResponseEntity.ok(productUpdate);
       }catch (NullPointerException e){
           log.error("NullPointerException coled user {} updated the product ",currentUser.getUser().getEmail());
           return ResponseEntity.notFound().build();
       }


    }
}
