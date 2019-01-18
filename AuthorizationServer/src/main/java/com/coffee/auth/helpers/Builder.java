package com.coffee.auth.helpers;

import com.coffee.model.auth.UserInfo;
import com.coffee.auth.entity.User;

import javax.annotation.Nonnull;

public class Builder {
    @Nonnull
    public static User buildUserByInfo(UserInfo userInfo) {
        User user = new User();
        user.setId(userInfo.getId());
        user.setUsername(userInfo.getUsername());
        user.setPassword(userInfo.getPassword());
        return user;
    }

    @Nonnull
    public static UserInfo buildUserInfo(User userInfo) {
        UserInfo rInfo = new UserInfo();
        rInfo.setId(userInfo.getId());
        rInfo.setUsername(userInfo.getUsername());
        rInfo.setPassword(userInfo.getPassword());
        return rInfo;
    }
}
