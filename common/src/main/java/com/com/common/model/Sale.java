package com.com.common.model;


import com.com.common.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//in progress
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Product product;
    private int productCount;

    @ManyToOne
    private User user;

    private Date saleDate;
}
