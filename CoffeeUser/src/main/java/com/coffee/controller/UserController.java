package com.coffee.controller;
import com.coffee.entity.User;
import com.coffee.model.UserInfo;
import com.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserInfo userById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @GetMapping
    public List<UserInfo> allUsers() {
        return userService.findAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserInfo userInfo) {
        User savedUser = userService.save(userInfo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UserInfo user, @PathVariable Integer id) {

        UserInfo userOptional = userService.findUserById(id);

        if (userOptional != null)
            return ResponseEntity.notFound().build();

        user.setId(id);

        userService.save(user);

        return ResponseEntity.noContent().build();
    }
}
