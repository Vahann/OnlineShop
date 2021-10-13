package com.com.common.service.impl;

import com.com.common.model.Product;
import com.com.common.repository.OrderRepository;
import com.com.common.repository.ProductRepository;
import com.com.common.repository.UserRepository;
import com.com.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;
        private final UserRepository userRepository;
        private final OrderRepository orderRepository;


    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findProductById(int id) {

            return productRepository.findById(id);
    }

//    @Override
//    public void updateProduct(Product product) {
//
//    }



//    @Override
//    public void addProduct(Product product, MultipartFile multipartFile) {

//        if (!multipartFile.isEmpty()) {
//            String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
//            try {
//                multipartFile.transferTo(new File(uploadDir + File.separator + picUrl));
//            } catch (IOException e) {
//                log.error("Exception while uploading file {} ", picUrl);
//
//            }
//            product.setPicUrl(picUrl);
//        }

//        List<Hashtag> hashtags = new ArrayList<>();
//        for (String s : hashtagList) {
//            var byName = hashtagService.findByName(s);
//            hashtags.add(byName);
//        }
//        book.setHashtags(hashtags);

//        product.setProductAddDate(new Date());
  //      productRepository.save(product);
   // }
//}

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
}
