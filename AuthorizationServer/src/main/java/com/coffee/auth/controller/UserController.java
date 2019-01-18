package com.coffee.auth.controller;
import java.util.List;

import com.coffee.auth.entity.User;
import com.coffee.auth.service.UserService;
import com.coffee.model.auth.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserInfo> listUser(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserInfo userById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @PostMapping("/")
    public UserInfo create(@RequestBody UserInfo user){
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") Integer id){
        userService.delete(id);
        return "success";
    }
}
