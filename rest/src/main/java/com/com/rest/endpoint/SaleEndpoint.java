package com.com.rest.endpoint;


import com.com.common.dto.request.SaleRequest;
import com.com.common.dto.response.SaleResponse;
import com.com.common.exception.ProductNotFoundException;
import com.com.common.exception.SaleNotCompletedException;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.Sale;
import com.com.common.service.SaleService;
import com.com.rest.security.CurrentUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleEndpoint {

    private final ModelMapper mapper;
    private final SaleService saleService;
    private final CurrentUserDetailsServiceImpl currentService;


    @GetMapping("/")
    public List<SaleResponse> getAllSales() throws UserNotFoundException {
        log.info("User {} open all sales ", currentService.currentUser().getEmail());
        return saleService.convertSale(saleService.findAllSales());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<SaleResponse>> searchSaleByProductId(@PathVariable("id") int id) throws UserNotFoundException {
        Optional<List<Sale>> saleByProductId = saleService.findSaleByProductId(id);
        if (saleByProductId.isEmpty()) {
            log.info("user {} tried to find an order by product id ", currentService.currentUser().getEmail());
            return ResponseEntity.notFound().build();
        }

        log.info("user {} looking for an order by product id {}", currentService.currentUser().getEmail(), saleByProductId.get());
        return ResponseEntity.ok(saleService.convertSale(saleByProductId.get()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<SaleResponse>> searchSaleByUserEmail(@PathVariable("email") String email) throws UserNotFoundException {
        Optional<List<Sale>> saleByUserEmail = saleService.findSalesByUserEmail(email);
        if (saleByUserEmail.isEmpty()) {
            log.info("user {} tried to find an order By User Email ", currentService.currentUser().getEmail());
            return ResponseEntity.notFound().build();
        }
        log.info("user {} looking for an order by User Email {}", currentService.currentUser().getEmail(), saleByUserEmail.get());
        return ResponseEntity.ok(saleService.convertSale(saleByUserEmail.get()));
    }

    // in progress

    //Add exception for Sale OK
    //Add productException for Enum OK
    //if productCount==0 OK

    //Add changeStatusFor Sale

    //  change category method in ProductEndpoint

    @PostMapping("/add/{productId}/{productCount}") ///{userId}
    public ResponseEntity<SaleResponse> addSale(@PathVariable("productId") int productId,
                                                @PathVariable("productCount") int productCount,
                                                @RequestBody SaleRequest saleRequest) throws ProductNotFoundException, UserNotFoundException, SaleNotCompletedException {
        return ResponseEntity.ok(mapper.map(saleService.addSale(mapper.map(saleRequest, Sale.class), productId,productCount, currentService.currentUser()), SaleResponse.class));
    }

    @GetMapping("/{saleStatus}")
    public ResponseEntity<List<SaleResponse>> searchSaleByStatus(@PathVariable("saleStatus") String saleStatus) {

        return ResponseEntity.ok(saleService.convertSale(saleService.searchSaleByStatus(saleStatus)));

    }

}
