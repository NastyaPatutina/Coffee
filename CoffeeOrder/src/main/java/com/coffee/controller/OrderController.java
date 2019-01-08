package com.coffee.controller;

import com.coffee.entity.Order;
import com.coffee.helpers.Builder;
import com.coffee.model.house.ProductInfo;
import com.coffee.model.order.order.*;
import com.coffee.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public OrderInfo orderById(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<OrderInfo> allOrders(@RequestParam("customer_id") Optional<Integer> userId, @RequestParam("coffee_house_id") Optional<Integer> coffeeHouseId) {
        if (userId.isPresent()) {
            return orderService.findOrderByCustomerId(userId.get());
        } else if(coffeeHouseId.isPresent()) {
            return orderService.findOrderByCoffeeHouseId(coffeeHouseId.get());
        } else {
            return orderService.findAllOrders();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<OrderInfo> createOrder(@RequestBody OrderMiniInfo orderInfo) {
        Order savedOrder = orderService.save(orderInfo);

        return new ResponseEntity<OrderInfo>(Builder.buildOrderInfo(savedOrder), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderInfo> updateOrder(@RequestBody OrderMiniInfo order, @PathVariable Integer id) {

        OrderInfo orderOptional = orderService.findOrderById(id);

        if (orderOptional == null)
            return ResponseEntity.notFound().build();

        order.setId(id);

        Order savedOrder = orderService.save(order);

        return new ResponseEntity<OrderInfo>(Builder.buildOrderInfo(savedOrder), HttpStatus.OK);
    }
}
