package com.com.common.dto.response;

import com.com.common.model.Category;
import com.com.common.model.enums.ProductForGender;
import com.com.common.model.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private int id;
    private String productName;
    private String description;
    private double price;
    private int count;

    @Enumerated(EnumType.STRING)
    private ProductForGender productForGender;
    @Enumerated(EnumType.STRING)
    private Size size;

    private Category category;
    private String picUrl;
}
