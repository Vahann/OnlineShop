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
    private final SaleRepository saleRepository;
    private final UserService userService;


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
    public ResponseEntity<Sale> searchSale(@PathVariable("id") int id) {
        Optional<Sale> saleByProductId = saleService.findSaleByProductId(id);
        if (saleByProductId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saleByProductId.get());
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Sale>> getSaleByUserEmail(@PathVariable("email") String email) {
//        Optional<User> userByEmail=userService.findUserByEmail(email);
        Optional<List<Sale>> saleByEmail = saleRepository.findSaleByUserEmail(email);
        if (saleByEmail.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saleByEmail.get());
    }
//    }
//
//  ///new test
//    //test 2
//    //test 2
//    @DeleteMapping("/{id}")
//    public ResponseEntity<User> deleteUserById(@PathVariable("id") int id) {
//        //service
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
//    }
//    }
}
