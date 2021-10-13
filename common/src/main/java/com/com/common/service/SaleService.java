package com.com.common.service;

import com.com.common.model.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    List<Sale> findAllSales();

    Optional<Sale> findSaleByProductId(int id);
}
