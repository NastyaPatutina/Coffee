package com.coffeegetaway.service.house;

import com.coffee.model.house.ProductInfo;
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
public class ProductServiceImpl implements ProductService {

    private String default_urlTarget = "http://localhost:8080/products/";

    @Override
    public ProductInfo findProductById(Integer id) {

        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        ProductInfo result = restTemplate.getForObject(urlTarget, ProductInfo.class);
        return result;
    }

    @Override
    public List<ProductInfo> allProducts() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<ProductInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductInfo>>(){});
        return result.getBody();
    }

    @Override
    public void deleteProduct(Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @Override
    public ResponseEntity<ProductInfo> updateProduct(ProductInfo productInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, ProductInfo.class);
        return result;
    }

    @Override
    public ResponseEntity<ProductInfo> createProduct(ProductInfo productInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo);
        ProductInfo result = restTemplate.postForObject(default_urlTarget, request, ProductInfo.class);
        if (result == null)
            return new ResponseEntity<>((ProductInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<ProductInfo>(result, HttpStatus.CREATED);    }
}
