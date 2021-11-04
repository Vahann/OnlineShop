package com.com.common.model;

import com.com.common.model.enums.ProductForGender;
import com.com.common.model.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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

    @ManyToOne
    private Category category;

    // private Date productAddDate; // update SQL

    private String picUrl;
}
