package com.coffeegetaway.controller;

import com.coffeegetaway.helpers.CoffeeRequest;
import org.json.JSONObject;
import com.coffee.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @GetMapping("/{id}")
    public OrderInfo orderById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = "http://localhost:8081/orders/" + id.toString();
        OrderInfo res = CoffeeRequest.generate(urlTarget, urlParameters,"GET");
        return res;
    }

    @GetMapping
    public List<OrderInfo> allOrders() {
        String urlParameters = "";
        String urlTarget = "http://localhost:8081/orders/";
        List<OrderInfo> res = CoffeeRequest.generate(urlTarget, urlParameters, "GET");
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {

    }

    @PostMapping("/")
    public ResponseEntity<Object> createOrder(@RequestBody OrderInfo orderInfo) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@RequestBody OrderInfo order, @PathVariable Integer id) {
        return null;
    }
}