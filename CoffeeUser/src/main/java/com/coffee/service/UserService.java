package com.coffee.service;

import com.coffee.entity.User;
import com.coffee.model.user.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface UserService {
    @Nonnull
    List<UserInfo> findAllUsers();

    @Nullable
    UserInfo findUserById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    User save(UserInfo userInfo);
}