package com.com.rest.endpoint;


import com.com.common.model.Sale;
import com.com.common.service.SaleService;

import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleEndpoint {

    //
    private final SaleService saleService;

    @GetMapping("/")
    public List<Sale> getAllSales() {
        return saleService.findAllSales();
//    }
//        @GetMapping("/{userEmail}")
//                public ResponseEntity<Sale> get
////
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
//        //service
//        Optional<User> userById = userRepository.findById(id);
//        if (userById.isEmpty()) {
//            return ResponseEntity
//                    .notFound()
//                    .build();
//        }
//
//        return ResponseEntity.ok(userById.get());
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
    }
}
