package com.com.common.dto.response;

import com.com.common.model.Product;
import com.com.common.model.User;
import com.com.common.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponse {

    private int id;
    @Enumerated(EnumType.STRING)
    private Status status;

    private Product product;
    private int productCount;


    private User user;

    private Date saleDate;
}
