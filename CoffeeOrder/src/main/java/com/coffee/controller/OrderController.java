package com.coffee.controller;

import com.coffee.entity.Order;
import com.coffee.model.OrderInfo;
import com.coffee.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<OrderInfo> allOrders(@RequestParam("user_id") Optional<Integer> userId, @RequestParam("coffee_house_id") Optional<Integer> coffeeHouseId) {
        if (userId.isPresent()) {
            return orderService.findOrderByUserId(userId.get());
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
    public ResponseEntity<Object> createOrder(@RequestBody OrderInfo orderInfo) {
        Order savedOrder = orderService.save(orderInfo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrder.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderInfo order, @PathVariable Integer id) {

        OrderInfo orderOptional = orderService.findOrderById(id);

        if (orderOptional == null)
            return ResponseEntity.notFound().build();

        order.setId(id);

        orderService.save(order);

        return ResponseEntity.noContent().build();
    }
}
