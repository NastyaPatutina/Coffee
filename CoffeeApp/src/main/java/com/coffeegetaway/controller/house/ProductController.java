package com.coffeegetaway.controller.house;

import com.coffee.model.house.ProductInfo;
import com.coffeegetaway.helpers.CoffeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8080/products/";

    @GetMapping("/{id}")
    public ProductInfo ProductById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        logger.info(res_requst);
        ProductInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, ProductInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public List<ProductInfo> allProducts() {

        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET", logger);
        logger.info(res_requst);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<ProductInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE", logger);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createProduct(@RequestBody ProductInfo productInfo) {
        return null;

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductInfo product, @PathVariable Integer id) {
        return null;
    }
}
