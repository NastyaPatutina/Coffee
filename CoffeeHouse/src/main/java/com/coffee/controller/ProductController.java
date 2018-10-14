package com.coffee.controller;

import com.coffee.entity.Product;
import com.coffee.model.ProductInfo;
import com.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductInfo ProductById(@PathVariable Integer id) {
        return productService.findProductById(id);
    }

    @GetMapping
    public List<ProductInfo> allProducts() {
        return productService.findAllProducts();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createProduct(@RequestBody Product productInfo) {
        Product savedProduct = productService.save(productInfo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable Integer id) {

        ProductInfo productOptional = productService.findProductById(id);

        if (productOptional != null)
            return ResponseEntity.notFound().build();

        product.setId(id);

        productService.save(product);

        return ResponseEntity.noContent().build();
    }
}
