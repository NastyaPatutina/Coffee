package com.coffeegetaway.service.customer;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.customer.CustomerInfo;
import com.coffeegetaway.ErrorModel;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private String default_urlTarget = "http://localhost:8082/customers/";

    @Override
    public CustomerInfo findCustomerById(Integer id) {

        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        CustomerInfo result;
        try {
            result = restTemplate.getForObject(urlTarget, CustomerInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return result;
    }

    @Override
    public List<OrderInfo> customerOrdersById(Integer id) {
        String urlTarget = "http://localhost:8081/orders/?used_id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<OrderInfo>> result;
        try {
            result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OrderInfo>>() {
                    });
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return result.getBody();
    }

    @Override
    public List<CustomerInfo> allCustomers() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<CustomerInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CustomerInfo>>(){});
        return result.getBody();
    }

    @Override
    public void deleteCustomer(Integer id) {
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
        }
    }

    @Override
    public ResponseEntity<CustomerInfo> updateCustomer(CustomerInfo customerInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<CustomerInfo> request = new HttpEntity<>(customerInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerInfo> result;
        try {
            result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, CustomerInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return result;
    }

    @Override
    public ResponseEntity<CustomerInfo> createCustomer(CustomerInfo customerInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<CustomerInfo> request = new HttpEntity<>(customerInfo);
        CustomerInfo result;
        try {
            result = restTemplate.postForObject(default_urlTarget, request, CustomerInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return new ResponseEntity<CustomerInfo>(result, HttpStatus.CREATED);    }
}
