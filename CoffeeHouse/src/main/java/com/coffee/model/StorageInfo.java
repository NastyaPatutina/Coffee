package com.coffee.model;

public class StorageInfo {
    private int id;
    private int count;
    private int product_id;
    private int house_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseId() {
        return house_id;
    }

    public void setHouseId(int house_id) {
        this.house_id = house_id;
    }

    public int getProductId() {
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
