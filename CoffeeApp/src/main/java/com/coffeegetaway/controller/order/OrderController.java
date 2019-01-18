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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public OrderInfo orderById(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<OrderInfo> allOrders(@RequestParam("customer_id") Optional<Integer> customerId,
                                     @RequestParam("coffee_house_id") Optional<Integer> coffeeHouseId) {
        if (customerId.isPresent()) {
            return orderService.allOrdersbyCustomer(customerId.get());
        }
        if (coffeeHouseId.isPresent()) {
            return orderService.allOrdersByCoffeeHouse(coffeeHouseId.get());
        }
        return orderService.allOrders();
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