package com.coffeegetaway.controller.user;

import com.coffee.model.OrderInfo;
import com.coffee.model.UserInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.helpers.CoffeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    private String default_urlTarget = "http://localhost:8082/users/";

    @GetMapping("/{id}")
    public UserInfo userById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        UserInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, UserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/{id}/orders")
    public List<OrderInfo> userOrdersById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        UserInfo user = null;
        try {
            user = objectMapper.readValue(res_requst, UserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        urlParameters = "";
        urlTarget = "http://localhost:8081/orders/?used_id=" + user.getId();
        objectMapper = new ObjectMapper();
        res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        List<OrderInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<OrderInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public List<UserInfo> allUsers() {
        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET", logger);
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<UserInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE", logger);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserInfo userInfo) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UserInfo user, @PathVariable Integer id) {
        return null;
    }
}
