package com.coffee.model;

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

    public Integer getHouseId() {
        return house.getId();
    }

    public void setHouseId(Integer house_id) {
        this.house.setId(house_id);
    }

    public Integer getProductId() {
        return product.getId();
    }

    public void setProductId(Integer product_id) {
        this.product.setId(product_id);
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
