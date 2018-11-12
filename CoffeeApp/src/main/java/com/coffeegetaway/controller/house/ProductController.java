package com.coffeegetaway.controller.house;

import com.coffee.model.house.ProductInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8080/products/";

    @GetMapping("/{id}")
    public ProductInfo ProductById(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        ProductInfo result = restTemplate.getForObject(urlTarget, ProductInfo.class);
        return result;
    }

    @GetMapping
    public List<ProductInfo> allProducts() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<ProductInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductInfo>>(){});
        return result.getBody();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @PostMapping("/")
    public ResponseEntity<ProductInfo> createProduct(@RequestBody ProductInfo productInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo);
        ProductInfo result = restTemplate.postForObject(default_urlTarget, request, ProductInfo.class);
        if (result == null)
            return new ResponseEntity<>((ProductInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<ProductInfo>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductInfo> updateProduct(@RequestBody ProductInfo product, @PathVariable Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<ProductInfo> request = new HttpEntity<>(product);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, ProductInfo.class);
        return result;

    }
}
