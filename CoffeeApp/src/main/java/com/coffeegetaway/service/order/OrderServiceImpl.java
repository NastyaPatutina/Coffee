package com.coffeegetaway.service.order;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.order.order.OrderMiniInfo;
import com.coffeegetaway.ErrorModel;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
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
        OrderInfo result;
        try {
            result = restTemplate.getForObject(urlTarget, OrderInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result;
    }

    @Override
    public List<OrderInfo> allOrders() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);
        ResponseEntity<List<OrderInfo>> result;
        try {
            result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OrderInfo>>() {
                    });
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result.getBody();
    }

    @Override
    public List<OrderInfo> allOrdersbyCustomer(Integer customerId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);

        builder.queryParam("customer_id", customerId);
        ResponseEntity<List<OrderInfo>> result;
        try {
            result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OrderInfo>>(){});
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result.getBody();
    }

    @Override
    public List<OrderInfo> allOrdersByCoffeeHouse(Integer coffeeHouseId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);

        builder.queryParam("coffee_house_id", coffeeHouseId);

        ResponseEntity<List<OrderInfo>> result;
        try {
            result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OrderInfo>>(){});
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result.getBody();
    }

    @Override
    public void deleteOrder(Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete (urlTarget,  params );
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
    }

    @Override
    public ResponseEntity<OrderInfo> updateOrder(OrderMiniInfo orderInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<OrderMiniInfo> request = new HttpEntity<>(orderInfo);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<OrderInfo> result;
        try {
            result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, OrderInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result;
    }

    @Override
    public ResponseEntity<OrderInfo> createOrder(OrderMiniInfo orderInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<OrderMiniInfo> request = new HttpEntity<>(orderInfo);
        OrderInfo result;
        try {
            result = restTemplate.postForObject(default_urlTarget, request, OrderInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return new ResponseEntity<OrderInfo>(result, HttpStatus.CREATED);
    }
}
