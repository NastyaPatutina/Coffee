package com.coffee.service.order;

import com.coffee.entity.Order;
import com.coffee.model.OrderInfo;
import com.coffee.repository.OrderRepository;
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
    private OrderRepository orderRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<OrderInfo> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::buildOrderInfo)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<OrderInfo> findOrderByUserId(@Nonnull Integer userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::buildOrderInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public OrderInfo findOrderById(@Nonnull Integer id) {
        return orderRepository.findById(id).map(this::buildOrderInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Order save(OrderInfo orderInfo) {
        return orderRepository.save(buildOrderByInfo(orderInfo));
    }

    @Nonnull
    private OrderInfo buildOrderInfo(Order order) {
        OrderInfo info = new OrderInfo();
        info.setId(order.getId());
        info.setUserId(order.getUserId());
        info.setRecipeId(order.getRecipeId());
        info.setCoffeeHouseId(order.getCoffeeHouseId());
        return info;
    }

    @Nonnull
    private Order buildOrderByInfo(OrderInfo orderInfo) {
        Order order = orderRepository.findById(orderInfo.getId()).orElse(null);
        order.setUserId(orderInfo.getUserId());
        order.setRecipeId(orderInfo.getRecipeId());
        order.setCoffeeHouseId(orderInfo.getCoffeeHouseId());
        return order;
    }
}
