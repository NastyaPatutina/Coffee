package com.coffeegetaway.controller.auth;

import com.coffee.model.auth.UserInfo;
import com.coffeegetaway.service.auth.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@PreAuthorize("isAuthenticated()")
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
    public ResponseEntity<UserInfo> create(@RequestBody UserInfo user){
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") Integer id){
        userService.delete(id);
        return "success";
    }

}
