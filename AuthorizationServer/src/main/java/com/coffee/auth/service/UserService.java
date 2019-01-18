package com.coffee.auth.service;

import com.coffee.auth.entity.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.coffee.model.auth.UserInfo;

import java.util.List;

public interface UserService {

    UserInfo save(UserInfo user);

    @Nonnull
    List<UserInfo> findAll();

    void delete(Integer id);

    @Nullable
    UserInfo findUserById(Integer id);
}