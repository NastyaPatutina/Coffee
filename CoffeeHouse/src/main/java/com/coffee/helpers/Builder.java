package com.coffee.helpers;

import com.coffee.entity.House;
import com.coffee.entity.Product;
import com.coffee.entity.Storage;
import com.coffee.model.HouseInfo;
import com.coffee.model.ProductInfo;
import com.coffee.model.StorageInfo;

import javax.annotation.Nonnull;

public class Builder {

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
    public static Storage buildStorageByInfo(StorageInfo storageInfo) {
        Storage storage = new Storage();
        storage.setId(storageInfo.getId());
        storage.setCount(storageInfo.getCount());
        storage.setHouse(Builder.buildHouseByInfo(storageInfo.getHouse()));
        storage.setProduct(Builder.buildProductByInfo(storageInfo.getProduct()));
        return storage;
    }
}
