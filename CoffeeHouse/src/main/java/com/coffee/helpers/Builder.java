package com.coffee.helpers;

import com.coffee.entity.House;
import com.coffee.entity.Product;
import com.coffee.model.HouseInfo;
import com.coffee.model.ProductInfo;

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
}
