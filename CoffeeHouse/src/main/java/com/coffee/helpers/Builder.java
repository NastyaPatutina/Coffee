package com.coffee.helpers;

import com.coffee.entity.House;
import com.coffee.entity.Product;
import com.coffee.entity.Storage;
import com.coffee.model.HouseInfo;
import com.coffee.model.ProductInfo;
import com.coffee.model.StorageInfo;
import com.coffee.model.StorageMiniInfo;
import com.coffee.repository.HouseRepository;
import com.coffee.repository.ProductRepository;
import com.coffee.service.house.HouseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

public class Builder {

    @Autowired
    private static HouseRepository houseRepository;

    @Autowired
    private static ProductRepository productRepository;

    @Nonnull
    public static ProductInfo buildProductInfo(Product product) {
        ProductInfo info = new ProductInfo();
        info.setId(product.getId());
        info.setName(product.getName());
        return info;
    }

    @Nonnull
    public static Product buildProductByInfo(ProductInfo productInfo) {
        Product product = new Product();
        product.setName(productInfo.getName());
        product.setId(productInfo.getId());
        return product;
    }

    @Nonnull
    public static HouseInfo buildHouseInfo(House house) {
        HouseInfo info = new HouseInfo();
        info.setName(house.getName());
        info.setId(house.getId());
        info.setAddress(house.getAddress());
        info.setLongitude(house.getLongitude());
        info.setLatitude(house.getLatitude());
        return info;
    }

    @Nonnull
    public static House buildHouseByInfo(HouseInfo houseInfo) {
        House house = new House();
        house.setName(houseInfo.getName());
        house.setId(houseInfo.getId());
        house.setAddress(houseInfo.getAddress());
        house.setLongitude(houseInfo.getLongitude());
        house.setLatitude(houseInfo.getLatitude());
        return house;
    }


    @Nonnull
    public static StorageInfo buildStorageInfo(Storage storage) {
        StorageInfo info = new StorageInfo();
        info.setId(storage.getId());
        info.setCount(storage.getCount());
        info.setHouse(Builder.buildHouseInfo(storage.getHouse()));
        info.setProduct(Builder.buildProductInfo(storage.getProduct()));
        return info;
    }

    @Nonnull
    public static StorageMiniInfo buildStorageMiniInfo(Storage storage) {
        StorageMiniInfo info = new StorageMiniInfo();
        info.setId(storage.getId());
        info.setCount(storage.getCount());
        info.setHouseId(storage.getHouse().getId());
        info.setProductId(storage.getProduct().getId());
        return info;
    }

    @Nonnull
    public static Storage buildStorageByInfo(StorageInfo storageInfo) {
        Storage storage = new Storage();
        storage.setId(storageInfo.getId());
        storage.setCount(storageInfo.getCount());
        storage.setHouse(Builder.buildHouseByInfo(storageInfo.getHouse()));
        storage.setProduct(Builder.buildProductByInfo(storageInfo.getProduct()));
        return storage;
    }

    @Nonnull
    public static Storage buildStorageByInfo(StorageMiniInfo storageInfo) {
        Storage storage = new Storage();
        storage.setId(storageInfo.getId());
        storage.setCount(storageInfo.getCount());
        storage.setHouse(houseRepository.findById(storageInfo.getHouseId()).orElse(null));
        storage.setProduct(productRepository.findById(storageInfo.getProductId()).orElse(null));
        return storage;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
