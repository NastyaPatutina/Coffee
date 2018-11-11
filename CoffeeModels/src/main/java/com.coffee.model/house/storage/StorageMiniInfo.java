package com.coffee.model.house.storage;

public class StorageMiniInfo {
    private Integer id;
    private Integer count;
    private Integer productId;
    private Integer houseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer product) {
        this.productId = product;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer house) {
        this.houseId = house;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
