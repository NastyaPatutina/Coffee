package com.coffee.service;

import com.coffee.entity.User;
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
                .map(this::buildUserInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public UserInfo findUserById(@Nonnull Integer id) {
        return userRepository.findById(id).map(this::buildUserInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User save(UserInfo userInfo) {
        return userRepository.save(buildUserByInfo(userInfo));
    }

    @Nonnull
    private UserInfo buildUserInfo(User user) {
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
    private User buildUserByInfo(UserInfo userInfo) {
        User user = userRepository.findById(userInfo.getId()).orElse(null);
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setGender(userInfo.getGender());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        return user;
    }
}
