package com.coffee.auth.service;

import com.coffee.auth.entity.User;

import java.util.List;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(Integer id);
}