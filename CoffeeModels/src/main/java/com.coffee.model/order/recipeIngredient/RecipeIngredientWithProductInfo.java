package com.coffee.model.order.recipeIngredient;

import com.coffee.model.house.ProductInfo;

public class RecipeIngredientWithProductInfo {
    private Integer id;
    private ProductInfo product;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product_id) {
        this.product = product_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
