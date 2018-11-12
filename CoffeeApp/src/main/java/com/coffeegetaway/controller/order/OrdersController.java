package com.coffeegetaway.controller.order;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.order.order.OrderMiniInfo;
import com.coffeegetaway.controller.house.ProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private String default_urlTarget = "http://localhost:8081/orders/";

    @GetMapping("/{id}")
    public OrderInfo orderById(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        OrderInfo result = restTemplate.getForObject(urlTarget, OrderInfo.class);
        return result;
    }

    @GetMapping
    public List<OrderInfo> allOrders(@RequestParam("user_id") Optional<Integer> userId,
                                     @RequestParam("coffee_house_id") Optional<Integer> coffeeHouseId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);

        if (userId.isPresent()) {
            builder.queryParam("user_id", userId);
        }
        if (coffeeHouseId.isPresent()) {
            builder.queryParam("coffee_house_id", coffeeHouseId);
        }

        ResponseEntity<List<OrderInfo>> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderInfo>>(){});
        return result.getBody();
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @PostMapping("/")
    public ResponseEntity<OrderInfo> createOrder(@RequestBody OrderMiniInfo order) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<OrderMiniInfo> request = new HttpEntity<>(order);
        OrderInfo result = restTemplate.postForObject(default_urlTarget, request, OrderInfo.class);
        if (result == null)
            return new ResponseEntity<>((OrderInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<OrderInfo>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderInfo> updateOrder(@RequestBody OrderMiniInfo order, @PathVariable Integer id) {

        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<OrderMiniInfo> request = new HttpEntity<>(order);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrderInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, OrderInfo.class);
        return result;
    }
}