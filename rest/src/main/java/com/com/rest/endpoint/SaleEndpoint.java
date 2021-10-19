package com.com.rest.endpoint;


import com.com.common.model.Sale;
import com.com.common.repository.SaleRepository;
import com.com.common.service.SaleService;
import com.com.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleEndpoint {

    private final SaleService saleService;
//    private final SaleRepository saleRepository;
//    private final UserService userService;


    @GetMapping("/")
    public List<Sale> getAllSales() {
        return saleService.findAllSales();
    }

    //    @GetMapping("/{email}")
//    public ResponseEntity<User> getSaleByUserEmail(@PathVariable("email") String email){
//        Optional<User> userByEmail=userService.findUserByEmail(email);
//        if (userByEmail.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(userByEmail.get());
//    }
    @GetMapping("/product/{id}")
    public ResponseEntity<List<Sale>> searchSaleByProductId(@PathVariable("id") int id) {
        Optional<List<Sale>> saleByProductId = saleService.findSaleByProductId(id);
        if (saleByProductId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saleByProductId.get());
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Sale>> searchSaleByUserEmail(@PathVariable("email") String email) {
        Optional<List<Sale>> saleByUserEmail=saleService.findSalesByUserEmail(email);
//        Optional<List<Sale>> saleByEmail = saleRepository.findSaleByUserEmail(email);
        if (saleByUserEmail.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saleByUserEmail.get());
    }

}
