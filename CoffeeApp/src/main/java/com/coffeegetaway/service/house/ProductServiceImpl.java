package com.coffeegetaway.service.house;

import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.ProductInfo;
import com.coffeegetaway.ErrorModel;
import com.coffeegetaway.service.auth.Authorize;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private String default_urlTarget = "http://localhost:8080/products/";

    private Authorize auth = new Authorize("http://localhost:8080/auth", "getaway", "getaway-house");

    @Override
    public ProductInfo findProductById(Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity(headers);

        String urlTarget = default_urlTarget + id.toString();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlTarget);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductInfo> result;
        try {
            result = restTemplate.exchange(
                    builder.build().encode().toUri(), HttpMethod.GET, request, ProductInfo.class);
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
    public List<ProductInfo> allProducts() {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ProductInfo>> result;
        try {
            result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<ProductInfo>>() {
                    });
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result.getBody();
    }

    @Override
    public void deleteProduct(Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        String urlTarget = default_urlTarget + id.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity( headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlTarget);

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.DELETE,
                    request,
                    String.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
    }

    @Override
    public ResponseEntity<ProductInfo> updateProduct(ProductInfo productInfo, Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        String urlTarget = default_urlTarget + id.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo,headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductInfo> result;
        try {
            result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, ProductInfo.class);
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
    public ResponseEntity<ProductInfo> createProduct(ProductInfo productInfo) {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo, headers);

        RestTemplate restTemplate = new RestTemplate();
        ProductInfo result;
        try {
            result = restTemplate.postForObject(default_urlTarget, request, ProductInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return new ResponseEntity<ProductInfo>(result, HttpStatus.CREATED);    }
}
