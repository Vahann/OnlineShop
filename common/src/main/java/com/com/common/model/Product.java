package com.com.common.model;

import com.com.common.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.Order;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productName;
    private String description;
    private double price;
    private int count;

    @Enumerated(EnumType.STRING)
    private ProductForGender productForGender;

    @Enumerated(EnumType.STRING)
    private Size size;

   // private Date productAddDate; // update SQL

    private String picUrl;
}
