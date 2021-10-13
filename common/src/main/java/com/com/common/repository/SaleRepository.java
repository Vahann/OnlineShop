package com.com.common.repository;

import com.com.common.model.Product;
import com.com.common.model.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale,Integer> {

//
    Optional<Sale> findSaleByProductId(int id);
}
