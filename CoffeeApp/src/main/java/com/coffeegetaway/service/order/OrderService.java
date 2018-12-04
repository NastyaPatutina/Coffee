package com.coffeegetaway.service.order;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.order.order.OrderMiniInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderInfo findOrderById(Integer id);

    List<OrderInfo> allOrders();

    List<OrderInfo> allOrdersbyUser(Integer userId);

    List<OrderInfo> allOrdersByCoffeeHouse(Integer coffeeHouseId);

    void deleteOrder(Integer id);

    ResponseEntity<OrderInfo> updateOrder(OrderMiniInfo orderInfo, Integer id);

    ResponseEntity<OrderInfo> createOrder(OrderMiniInfo orderInfo);
}
