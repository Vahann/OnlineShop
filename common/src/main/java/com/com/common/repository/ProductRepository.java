package com.com.common.repository;

import com.com.common.model.Product;
import com.com.common.model.enums.ProductForGender;
import com.com.common.model.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductByCategoryName(String categoryName);

    List<Product> findProductByProductName(String productName);

    List<Product> findProductByProductForGender(ProductForGender gender);
//Select * From Таблица Where Цена Between [Начальная цена] and [Конечная цена]

//    SELECT * FROM instructor
//  WHERE salary BETWEEN 50000 AND 100000;
//     @Query("select * from youTable where type == :type and price < :price")
//     ;
    List<Product> findProductByPriceBetween(double startPrice,double endPrice);

    List<Product> findProductBySize(Size size);

}
