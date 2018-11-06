package com.coffee.helpers;

import com.coffee.entity.User;
import com.coffee.model.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;

public class Builder {
    @Nonnull
    public static UserInfo buildUserInfo(User user) {
        UserInfo info = new UserInfo();
        info.setId(user.getId());
        info.setFirstName(user.getFirstName());
        info.setLastName(user.getLastName());
        info.setGender(user.getGender());
        info.setEmail(user.getEmail());
        info.setPhone(user.getPhone());
        return info;
    }

    @Nonnull
    public static User buildUserByInfo(UserInfo userInfo) {
        User user = new User();
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setGender(userInfo.getGender());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        return user;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
