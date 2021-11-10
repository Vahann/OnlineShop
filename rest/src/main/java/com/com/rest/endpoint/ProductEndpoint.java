package com.com.rest.endpoint;


import com.com.common.dto.ProductDto;
import com.com.common.dto.ProductSaveDto;
import com.com.common.exception.ProductNotFoundException;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.Product;
import com.com.common.service.ProductService;
import com.com.rest.security.CurrentUser;
import com.com.rest.security.CurrentUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private final CurrentUserDetailsServiceImpl currentService;
    private final ModelMapper mapper;


    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/")
    public List<ProductDto> getAllProducts() throws UserNotFoundException {
        log.info("user {} call method get all product", currentService.currentUser().getEmail());
        return productService.convertProduct(productService.findAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") int id) throws ProductNotFoundException, UserNotFoundException {
        log.info("User {} searching product by id {}", currentService.currentUser().getEmail(), id);
        return ResponseEntity.ok(mapper.map(productService.findProductById(id), ProductDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductSaveDto> deleteProductById(@PathVariable("id") int id)
            throws ProductNotFoundException, UserNotFoundException {
        if (productService.nullifyProduct(id)) {
            log.info("User {} deleted product by id {} ",
                    currentService.currentUser().getEmail(), id);
            return ResponseEntity.noContent().build();
        }
        log.warn("User {} tried to nullify product by id {}", currentService.currentUser().getEmail(), id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProducts(@RequestBody @Valid ProductSaveDto productSaveDto) throws UserNotFoundException {
        if (productSaveDto == null) {
            log.warn("user {} tried to add a product is null", currentService.currentUser().getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            Product product = productService.addProduct(mapper.map(productSaveDto, Product.class));
            log.info("user {} to add a product{} ", currentService.currentUser().getEmail(), productSaveDto);
            return ResponseEntity.ok(mapper.map(product, ProductDto.class));
        }
    }

    @PostMapping("/addImage/{productId}")
    public ResponseEntity addImage(@PathVariable("productId") int productId, @RequestParam(value = "image") MultipartFile multipartFile) throws IOException, ProductNotFoundException, UserNotFoundException {

        Product productById = productService.findProductById(productId);

        String pic = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadDir + File.separator + pic));

        productById.setPicUrl(pic);
        productService.addProduct(productById);
        log.info("user {} to add image a product {} ", currentService.currentUser().getEmail(), productById);
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
    public ResponseEntity<ProductDto> updateProductById(@PathVariable("id") int id,
                                                        @RequestBody ProductSaveDto productSaveDto,
                                                        @AuthenticationPrincipal CurrentUser currentUser) throws ProductNotFoundException {
        Product product = productService.updateProduct(id, mapper.map(productSaveDto, Product.class));
        log.info("user {} to update a product{} ", currentUser.getUser().getEmail(), productSaveDto);
        return ResponseEntity.ok(mapper.map(product, ProductDto.class));
    }

    @GetMapping("/productFilter/{variable}/method/{method}")
    public List<ProductDto> productFilter(@PathVariable("variable") String variable,
                                          @PathVariable("method") String method) {

        return productService.convertProduct(productService.filterForProduct(variable, method));
    }

    @GetMapping("/price/{startPrice}/{endPrice}")
    public List<ProductDto> searchProductByPrice(@PathVariable("startPrice") double startPrice,
                                                 @PathVariable("endPrice") double endPrice) {

        return productService.convertProduct(productService.findProductByPriceBetween(startPrice, endPrice));
    }
}
