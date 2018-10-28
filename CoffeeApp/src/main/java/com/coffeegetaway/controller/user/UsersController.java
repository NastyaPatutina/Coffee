package com.coffeegetaway.controller.user;

import com.coffee.model.UserInfo;
import com.coffeegetaway.helpers.CoffeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private String default_urlTarget = "http://localhost:8081/storage/";

    @GetMapping("/{id}")
    public UserInfo userById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET");
        UserInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, UserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public List<UserInfo> allUsers() {
        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET");
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
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE");
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
