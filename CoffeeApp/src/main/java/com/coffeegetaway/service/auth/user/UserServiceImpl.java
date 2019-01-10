package com.coffeegetaway.service.auth.user;

import com.coffee.model.auth.UserInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private String default_urlTarget = "http://localhost:8088/users/";

    @Override
    public ResponseEntity<UserInfo> save(UserInfo user) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<UserInfo> request = new HttpEntity<>(user);
        UserInfo result = restTemplate.postForObject(default_urlTarget, request, UserInfo.class);
        if (result == null)
            return new ResponseEntity<>((UserInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<UserInfo>(result, HttpStatus.CREATED);
    }

    @Nonnull
    @Override
    public List<UserInfo> findAll() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<UserInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserInfo>>(){});
        return result.getBody();
    }

    @Override
    public void delete(Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @Nullable
    @Override
    public UserInfo findUserById(Integer id) {

        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        UserInfo result = restTemplate.getForObject(urlTarget, UserInfo.class);
        return result;    }
}
