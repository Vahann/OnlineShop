package com.com.rest.endpoint;


import com.com.common.model.Sale;
import com.com.common.repository.SaleRepository;
import com.com.common.service.SaleService;
import com.com.common.service.UserService;
import com.com.rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sale")
public class SaleEndpoint {

    private final SaleService saleService;
//    private final SaleRepository saleRepository;
//    private final UserService userService;


    @GetMapping("/")
    public List<Sale> getAllSales( @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("the user {} looks at all orders ", currentUser.getUser().getEmail() );
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
    public ResponseEntity<List<Sale>> searchSaleByProductId(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<List<Sale>> saleByProductId = saleService.findSaleByProductId(id);
        if (saleByProductId.isEmpty()) {
            log.info("user {} searches for a non-existent order by product id ", currentUser.getUser().getEmail());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saleByProductId.get());
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Sale>> searchSaleByUserEmail(@PathVariable("email") String email,
                                                            @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<List<Sale>> saleByUserEmail=saleService.findSalesByUserEmail(email);
//        Optional<List<Sale>> saleByEmail = saleRepository.findSaleByUserEmail(email);
        if (saleByUserEmail.isEmpty()) {
            log.info("user {} searches for a non-existent order by email ", currentUser.getUser().getEmail());
            return ResponseEntity.notFound().build();
        }
        log.info("the user {} is looking for an order by email", currentUser.getUser().getEmail());
        return ResponseEntity.ok(saleByUserEmail.get());
    }

}
