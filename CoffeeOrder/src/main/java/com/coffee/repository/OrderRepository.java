package com.coffee.repository;

import com.coffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    List<Order> findByCoffeeHouseId(Integer coffeeHouseId);
}

