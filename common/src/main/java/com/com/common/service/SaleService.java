package com.com.common.service;

import com.com.common.dto.response.SaleResponse;
import com.com.common.exception.ProductNotFoundException;
import com.com.common.exception.SaleNotCompletedException;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.Sale;
import com.com.common.model.User;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    List<Sale> findAllSales();

    Optional<List<Sale>> findSaleByProductId(int id);

    Optional<List<Sale>> findSalesByUserEmail(String email);

    Sale addSale(Sale sale, int productId,int count, User user) throws ProductNotFoundException, UserNotFoundException, SaleNotCompletedException;

    List<Sale> searchSaleByStatus(String status);

    List<SaleResponse> convertSale(List<Sale> saleList);
}
