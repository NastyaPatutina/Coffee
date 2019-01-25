package com.coffee.controller;

import com.coffee.entity.Product;
import com.coffee.entity.Storage;
import com.coffee.helpers.Builder;
import com.coffee.model.house.*;
import com.coffee.model.house.storage.StorageMiniInfo;
import com.coffee.service.house.HouseService;
import com.coffee.service.product.ProductService;
import com.coffee.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ProductInfo> createProduct(@RequestBody ProductInfo productInfo) {
        Product savedProduct = productService.saveAndAddToStorage(productInfo);

        return new ResponseEntity<ProductInfo>(Builder.buildProductInfo(savedProduct), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductInfo> updateProduct(@RequestBody ProductInfo product, @PathVariable Integer id) {

        ProductInfo productOptional = productService.findProductById(id);

        if (productOptional == null)
            return ResponseEntity.notFound().build();

        product.setId(id);

        Product savedProduct = productService.save(product);

        return new ResponseEntity<ProductInfo>(Builder.buildProductInfo(savedProduct), HttpStatus.OK);
    }

    @PostMapping("/{id}/rollback")
    public void RollbackProductById(@PathVariable Integer id) {
        productService.deleteById(id);
    }
}
