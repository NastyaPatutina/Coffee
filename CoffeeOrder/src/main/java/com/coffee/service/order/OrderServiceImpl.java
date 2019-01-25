package com.coffee.service.order;

import com.coffee.entity.Order;
import com.coffee.helpers.Builder;
import com.coffee.model.order.order.*;
import com.coffee.repository.OrderRepository;
import com.coffee.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public List<OrderInfo> findOrderByCustomerId(@Nonnull Integer userId) {
        return orderRepository.findByCustomerId(userId)
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
        Order order;
        try {
            order = orderRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Order not found", ex);
        }
        return Builder.buildOrderInfo(order);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        Order order;
        try {
            order = orderRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Order not found", ex);
        }
        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public Order save(OrderMiniInfo orderInfo) {
        Order order;
        try {
            order = orderRepository.save(Builder.buildOrderByInfo(orderInfo, recipeRepository.findById(orderInfo.getRecipeId()).get()));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Error save order: " + ex.getCause().getCause().getMessage(), ex);
        }
        return order;
    }
}
