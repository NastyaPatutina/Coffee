package com.coffee.service;

import com.coffee.entity.Product;
import com.coffee.model.ProductInfo;
import com.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<ProductInfo> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::buildProductInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public ProductInfo findProductById(@Nonnull Integer id) {
        return productRepository.findById(id).map(this::buildProductInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Nonnull
    private ProductInfo buildProductInfo(Product product) {
        ProductInfo info = new ProductInfo();
        info.setId(product.getId());
        info.setName(product.getName());
        return info;
    }
}