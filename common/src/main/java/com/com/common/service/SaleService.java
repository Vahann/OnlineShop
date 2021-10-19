package com.com.common.service;

import com.com.common.model.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    List<Sale> findAllSales();

    Optional<List<Sale>> findSaleByProductId(int id);

    Optional<List<Sale>> findSalesByUserEmail(String email);
}
