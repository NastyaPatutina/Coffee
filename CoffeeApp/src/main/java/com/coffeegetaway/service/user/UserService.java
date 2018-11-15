package com.coffeegetaway.service.user;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.user.UserInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserInfo findUserById(Integer id);

    List<OrderInfo> userOrdersById(Integer id);

    List<UserInfo> allUsers();

    void deleteUser(Integer id);

    ResponseEntity<UserInfo> updateUser(UserInfo userInfo, Integer id);

    ResponseEntity<UserInfo> createUser(UserInfo userInfo);
}
