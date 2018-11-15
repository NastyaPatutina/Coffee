package com.coffeegetaway.controller.user;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.user.UserInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.service.user.UserService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserInfo userById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @GetMapping("/{id}/orders")
    public List<OrderInfo> userOrdersById(@PathVariable Integer id) {
        return userService.userOrdersById(id);
    }

    @GetMapping
    public List<UserInfo> allUsers() {
        return userService.allUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PostMapping("/")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserInfo userInfo) {
        return userService.createUser(userInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserInfo> updateUser(@RequestBody UserInfo user, @PathVariable Integer id) {
        return  userService.updateUser(user, id);
    }
}
