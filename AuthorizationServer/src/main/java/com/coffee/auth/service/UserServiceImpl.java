package com.coffee.auth.service;

import com.coffee.auth.entity.User;
import com.coffee.auth.helpers.Builder;
import com.coffee.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.coffee.model.auth.UserInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service(value="userService")
public class UserServiceImpl  implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userId);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Nonnull
    @Override
    public List<UserInfo> findAll() {
        return new ArrayList<>(userRepository.findAll().stream()
                .map(Builder::buildUserInfo)
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Nullable
    @Override
    public UserInfo findUserById(Integer id) {
        return userRepository.findById(id).map(Builder::buildUserInfo).orElse(null);
    }

    @Override
    public UserInfo save(UserInfo user) {
        return Builder.buildUserInfo(userRepository.save(Builder.buildUserByInfo(user)));
    }
}