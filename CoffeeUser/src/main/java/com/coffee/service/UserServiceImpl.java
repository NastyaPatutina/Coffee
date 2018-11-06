package com.coffee.service;

import com.coffee.entity.User;
import com.coffee.helpers.Builder;
import com.coffee.model.UserInfo;
import com.coffee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<UserInfo> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(Builder::buildUserInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public UserInfo findUserById(@Nonnull Integer id) {
        return userRepository.findById(id).map(Builder::buildUserInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User save(UserInfo userInfo) {
        return userRepository.save(Builder.buildUserByInfo(userInfo));
    }
}
