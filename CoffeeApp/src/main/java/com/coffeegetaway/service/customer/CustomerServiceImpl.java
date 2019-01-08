package com.coffeegetaway.service.customer;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.customer.CustomerInfo;
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
public class CustomerServiceImpl implements CustomerService {

    private String default_urlTarget = "http://localhost:8082/customers/";

    @Override
    public CustomerInfo findCustomerById(Integer id) {

        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        CustomerInfo result = restTemplate.getForObject(urlTarget, CustomerInfo.class);
        return result;
    }

    @Override
    public List<OrderInfo> customerOrdersById(Integer id) {
        String urlTarget = "http://localhost:8081/orders/?used_id=" + id;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<OrderInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderInfo>>(){});
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
        restTemplate.delete (urlTarget,  params );
    }

    @Override
    public ResponseEntity<CustomerInfo> updateCustomer(CustomerInfo customerInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<CustomerInfo> request = new HttpEntity<>(customerInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, CustomerInfo.class);
        return result;
    }

    @Override
    public ResponseEntity<CustomerInfo> createCustomer(CustomerInfo customerInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<CustomerInfo> request = new HttpEntity<>(customerInfo);
        CustomerInfo result = restTemplate.postForObject(default_urlTarget, request, CustomerInfo.class);
        if (result == null)
            return new ResponseEntity<>((CustomerInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<CustomerInfo>(result, HttpStatus.CREATED);    }
}
