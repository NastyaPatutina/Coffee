package com.coffee.repository;

import com.coffee.entity.Order;
import com.coffee.model.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);

}

