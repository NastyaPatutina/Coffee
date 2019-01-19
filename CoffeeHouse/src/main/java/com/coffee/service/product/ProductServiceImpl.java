package com.coffee.service.product;

import com.coffee.entity.House;
import com.coffee.entity.Product;
import com.coffee.entity.Storage;
import com.coffee.helpers.Builder;
import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.ProductInfo;
import com.coffee.model.house.storage.StorageMiniInfo;
import com.coffee.repository.HouseRepository;
import com.coffee.repository.ProductRepository;
import com.coffee.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<ProductInfo> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(Builder::buildProductInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public ProductInfo findProductById(@Nonnull Integer id) {
        return productRepository.findById(id).map(Builder::buildProductInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        List<Storage> st = storageRepository.findByProduct_Id(id);
        for (Storage sti: st) {
            storageRepository.delete(sti);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product saveAndAddToStorage(ProductInfo product) {
        Product savedProduct = productRepository.save(Builder.buildProductByInfo(product));

        if (savedProduct == null){
            return null;
        }

        List<House> houses = houseRepository.findAll();

        for (House house: houses) {
            Storage storage = new Storage();
            storage.setProduct(savedProduct);
            storage.setCount(0);
            storage.setHouse(house);
            storageRepository.save(storage);
        }
        return savedProduct;
    }


    @Override
    @Transactional
    public Product save(ProductInfo product) {
        return productRepository.save(Builder.buildProductByInfo(product));
    }
}