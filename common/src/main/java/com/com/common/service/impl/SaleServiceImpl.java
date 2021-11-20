package com.com.common.service.impl;

import com.com.common.dto.response.SaleResponse;
import com.com.common.exception.ProductNotFoundException;
import com.com.common.exception.SaleNotCompletedException;
import com.com.common.model.Product;
import com.com.common.model.Sale;
import com.com.common.model.User;
import com.com.common.model.enums.Status;
import com.com.common.repository.SaleRepository;
import com.com.common.service.ProductService;
import com.com.common.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final ModelMapper mapper;

    @Override
    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Optional<List<Sale>> findSaleByProductId(int id) {
        return saleRepository.findSaleByProductId(id);
    }

        @Override
    public Optional<List<Sale>> findSalesByUserEmail(String email) {
        return saleRepository.findSaleByUserEmail(email);
    }

    @Override
    public Sale addSale(int productId, int requestProductCount, User user) throws ProductNotFoundException, SaleNotCompletedException {
        Sale sale = new Sale();
        Product productFromDB = productService.findProductById(productId);
        if (productFromDB.getCount() > requestProductCount && user.isActiveProfile()) {
            sale.setProduct(productFromDB);
            sale.setUser(user);
            sale.setQuantity(requestProductCount);
            /// hard code
            sale.setStatus(Status.IN_PROCESS);
            sale.setSaleDate(new Date());

            productFromDB.setCount(productFromDB.getCount() - requestProductCount);
            productService.addProduct(productFromDB);

        } else {
            throw new SaleNotCompletedException();
        }

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> searchSaleByStatus(String status) {
        List<Sale> saleList = new ArrayList<>();
        try {
            saleList = saleRepository.findSaleByStatus(Status.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        }
        return saleList;
    }

    @Override
    public List<SaleResponse> convertSale(List<Sale> saleList) {

        List<SaleResponse> saleResponses = new ArrayList<>();
        for (Sale sale : saleList) {
            SaleResponse saleDto = mapper.map(sale, SaleResponse.class);
            saleResponses.add(saleDto);
        }
        return saleResponses;
    }

}
