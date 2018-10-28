package com.coffeegetaway.controller.order;

import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.helpers.CoffeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coffee.model.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    private String default_urlTarget = "http://localhost:8081/orders/";

    @GetMapping("/{id}")
    public OrderInfo orderById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        OrderInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, OrderInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public List<OrderInfo> allOrders() {
        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET", logger);
        ObjectMapper objectMapper = new ObjectMapper();
        List<OrderInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<OrderInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE", logger);
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