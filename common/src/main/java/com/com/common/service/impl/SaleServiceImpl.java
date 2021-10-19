package com.com.common.service.impl;

import com.com.common.model.Sale;
import com.com.common.repository.SaleRepository;
import com.com.common.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

   private final SaleRepository saleRepository;

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
}
