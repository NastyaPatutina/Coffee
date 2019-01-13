package com.coffeegetaway.controller.house;

import com.coffee.model.house.ProductInfo;
import com.coffeegetaway.service.house.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/products")
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductInfo productById(@PathVariable Integer id) {
        return productService.findProductById(id);
    }

    @GetMapping
    public List<ProductInfo> allProducts() {
        return productService.allProducts();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/")
    public ResponseEntity<ProductInfo> createProduct(@RequestBody ProductInfo productInfo) {
        return productService.createProduct(productInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductInfo> updateProduct(@RequestBody ProductInfo product, @PathVariable Integer id) {
        return productService.updateProduct(product, id);
    }
}
