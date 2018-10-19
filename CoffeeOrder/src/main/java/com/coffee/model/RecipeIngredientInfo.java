package com.coffee.model;


public class RecipeIngredientInfo {
    private Integer id;
    private Integer product_id;
    private Integer recipe_id;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
