package com.com.rest.endpoint;


import com.com.common.model.Product;

import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    //
    private final ProductService productService;

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
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
        if (productService.nullifyProduct(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

//       Optional<User> userById=userRepository.findById(id);
//        if (userById.isEmpty()) {
//            return ResponseEntity
//                    .notFound()
//                    .build();
//        }
//        User user=userById.get();
//        user.setActiveProfile(false);
//        userRepository.save(user);
////        userRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
    }
}
