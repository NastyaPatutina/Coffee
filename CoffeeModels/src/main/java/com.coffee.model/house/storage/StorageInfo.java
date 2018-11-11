package com.coffee.model.house.storage;

import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.ProductInfo;

public class StorageInfo {
    private Integer id;
    private Integer count;
    private ProductInfo product;
    private HouseInfo house;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product) {
        this.product = product;
    }

    public HouseInfo getHouse() {
        return house;
    }

    public void setHouse(HouseInfo house) {
        this.house = house;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
