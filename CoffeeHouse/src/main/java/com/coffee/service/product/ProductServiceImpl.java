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
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        Product product;
        try {
            product = productRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found", ex);
        }
        return Builder.buildProductInfo(product);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        Product product;
        try {
            product = productRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found", ex);
        }

        List<Storage> st = storageRepository.findByProduct_Id(id);
        for (Storage sti: st) {
            storageRepository.delete(sti);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product saveAndAddToStorage(ProductInfo product) {
        Product savedProduct = save(product);

        List<House> houses = houseRepository.findAll();

        for (House house: houses) {
            Storage storage = new Storage();
            storage.setProduct(savedProduct);
            storage.setCount(0);
            storage.setHouse(house);
            try {
                storageRepository.save(storage);
            } catch (Exception ex) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE,
                        "Error save storage for product " + product.getName() + ": " + ex.getCause().getCause().getMessage(), ex);
            }
        }
        return savedProduct;
    }


    @Override
    @Transactional
    public Product save(ProductInfo product) {
        Product savedProduct = null;
        Product productInDB = null;
        if (product.getId() == null)
            productInDB = productRepository.findByName(product.getName());

        if (productInDB == null) {
            try {
                savedProduct = productRepository.save(Builder.buildProductByInfo(product));
            } catch (Exception ex) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE, "Error save product: " + ex.getCause().getCause().getMessage(), ex);
            }
        }
        return savedProduct;
    }
}