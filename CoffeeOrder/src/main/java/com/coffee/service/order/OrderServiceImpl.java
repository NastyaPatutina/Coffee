package com.coffee.service.order;

import com.coffee.entity.Order;
import com.coffee.helpers.Builder;
import com.coffee.model.order.order.*;
import com.coffee.repository.OrderRepository;
import com.coffee.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<OrderInfo> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(Builder::buildOrderInfo)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<OrderInfo> findOrderByUserId(@Nonnull Integer userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(Builder::buildOrderInfo)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<OrderInfo> findOrderByCoffeeHouseId(@Nonnull Integer coffeeHouseId) {
        return orderRepository.findByCoffeeHouseId(coffeeHouseId)
                .stream()
                .map(Builder::buildOrderInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public OrderInfo findOrderById(@Nonnull Integer id) {
        return orderRepository.findById(id).map(Builder::buildOrderInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Order save(OrderMiniInfo orderInfo) {
        return orderRepository.save(Builder.buildOrderByInfo(orderInfo, recipeRepository.findById(orderInfo.getRecipeId()).orElse(null)));
    }
}
