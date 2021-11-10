package com.com.common.dto;

import com.com.common.model.Category;
import com.com.common.model.enums.ProductForGender;
import com.com.common.model.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSaveDto {

//    @Max(33)
    private String productName;
    private String description;
//    @NotNull
    private double price;
//    @NotNull
    private int count;

    @Enumerated(EnumType.STRING)
    private ProductForGender productForGender;
//    @NotNull
    @Enumerated(EnumType.STRING)
    private Size size;
//    @NotNull
    private Category category;
//  @NotNull
    private String picUrl;

}
