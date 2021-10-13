package com.com.common.service.impl;

import com.com.common.model.Sale;
import com.com.common.repository.SaleRepository;
import com.com.common.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

   private final SaleRepository saleRepository;

    @Override
    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }


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
//
//    @Override
//    public boolean nullifyProduct(int id) {    //deleteProduct
//        Optional<Product> productById = productRepository.findById(id);
//        if (productById.isPresent()) {
//            Product product = productById.get();
//            product.setCount(0);
//            productRepository.save(product);
//            return true;
//        }
//        return false;
//    }
}
