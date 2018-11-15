package com.coffeegetaway.controller.order;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.order.order.OrderMiniInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public OrderInfo orderById(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<OrderInfo> allOrders(@RequestParam("user_id") Optional<Integer> userId,
                                     @RequestParam("coffee_house_id") Optional<Integer> coffeeHouseId) {
        return orderService.allOrders(userId, coffeeHouseId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);

    }

    @PostMapping("/")
    public ResponseEntity<OrderInfo> createOrder(@RequestBody OrderMiniInfo order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderInfo> updateOrder(@RequestBody OrderMiniInfo order, @PathVariable Integer id) {
        return orderService.updateOrder(order, id);
    }
}