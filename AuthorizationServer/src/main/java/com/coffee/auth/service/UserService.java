package com.coffee.auth.service;

import com.coffee.auth.entity.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface UserService {

    User save(User user);

    @Nonnull
    List<User> findAll();

    void delete(Integer id);

    @Nullable
    User findUserById(Integer id);
}