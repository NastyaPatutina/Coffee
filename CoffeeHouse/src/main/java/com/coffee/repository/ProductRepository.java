package com.coffee.repository;

import com.coffee.entity.Product;
import com.coffee.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Integer> {
    Product findByName(String name);

}
