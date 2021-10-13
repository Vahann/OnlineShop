package com.com.common.repository;

import com.com.common.model.Order;
import com.com.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
