package com.com.web.controller;

import com.com.common.dto.response.ProductResponse;
import com.com.common.dto.response.SaleResponse;
import com.com.common.exception.ProductNotFoundException;
import com.com.common.exception.UserNotFoundException;
import com.com.common.model.Sale;
import com.com.common.repository.SaleRepository;
import com.com.common.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final SaleService saleService;
    private final SaleRepository saleRepository;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String  getAllSales(ModelMap modelMap) throws UserNotFoundException {
//        log.info("user {} call method get all product", currentService.currentUser().getEmail());
        List<SaleResponse> sales=saleService.convertSale(saleService.findAllSales());
       //List<Sale>sales = saleService.findAllSales();
        modelMap.addAttribute("sales", sales);
        return "orders";
    }

    @GetMapping("/orders/delete")
    public String del(@RequestParam("id") int id){
        saleRepository.deleteById(id);
        return "redirect:/orders";
    }
}
