package com.coffeegetaway.service.user;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.user.UserInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private String default_urlTarget = "http://localhost:8082/users/";

    @Override
    public UserInfo findUserById(Integer id) {

        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        UserInfo result = restTemplate.getForObject(urlTarget, UserInfo.class);
        return result;
    }

    @Override
    public List<OrderInfo> userOrdersById(Integer id) {
        String urlTarget = "http://localhost:8081/orders/?used_id=" + id;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<OrderInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderInfo>>(){});
        return result.getBody();
    }

    @Override
    public List<UserInfo> allUsers() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<UserInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserInfo>>(){});
        return result.getBody();
    }

    @Override
    public void deleteUser(Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @Override
    public ResponseEntity<UserInfo> updateUser(UserInfo userInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<UserInfo> request = new HttpEntity<>(userInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, UserInfo.class);
        return result;
    }

    @Override
    public ResponseEntity<UserInfo> createUser(UserInfo userInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<UserInfo> request = new HttpEntity<>(userInfo);
        UserInfo result = restTemplate.postForObject(default_urlTarget, request, UserInfo.class);
        if (result == null)
            return new ResponseEntity<>((UserInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<UserInfo>(result, HttpStatus.CREATED);    }
}
