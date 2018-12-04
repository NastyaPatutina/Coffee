package com.coffeegetaway.service.order;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.order.order.OrderMiniInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private String default_urlTarget = "http://localhost:8081/orders/";

    @Override
    public OrderInfo findOrderById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        OrderInfo result = restTemplate.getForObject(urlTarget, OrderInfo.class);
        return result;
    }

    @Override
    public List<OrderInfo> allOrders() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);
        ResponseEntity<List<OrderInfo>> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderInfo>>(){});
        return result.getBody();
    }

    @Override
    public List<OrderInfo> allOrdersbyUser(Integer userId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);

        builder.queryParam("user_id", userId);

        ResponseEntity<List<OrderInfo>> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderInfo>>(){});
        return result.getBody();
    }

    @Override
    public List<OrderInfo> allOrdersByCoffeeHouse(Integer coffeeHouseId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);

        builder.queryParam("coffee_house_id", coffeeHouseId);

        ResponseEntity<List<OrderInfo>> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderInfo>>(){});
        return result.getBody();
    }

    @Override
    public void deleteOrder(Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @Override
    public ResponseEntity<OrderInfo> updateOrder(OrderMiniInfo orderInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<OrderMiniInfo> request = new HttpEntity<>(orderInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrderInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, OrderInfo.class);
        return result;
    }

    @Override
    public ResponseEntity<OrderInfo> createOrder(OrderMiniInfo orderInfo) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<OrderMiniInfo> request = new HttpEntity<>(orderInfo);
        OrderInfo result = restTemplate.postForObject(default_urlTarget, request, OrderInfo.class);
        if (result == null)
            return new ResponseEntity<>((OrderInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<OrderInfo>(result, HttpStatus.CREATED);
    }
}
