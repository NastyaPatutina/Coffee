package com.coffeegetaway.service.auth.user;

import com.coffee.model.auth.UserInfo;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface UserService {

    ResponseEntity<UserInfo> save(UserInfo user);

    @Nonnull
    List<UserInfo> findAll();

    void delete(Integer id);

    @Nullable
    UserInfo findUserById(Integer id);
}
