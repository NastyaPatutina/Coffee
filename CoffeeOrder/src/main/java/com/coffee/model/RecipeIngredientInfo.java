package com.coffee.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class RecipeIngredientInfo {
    private Integer id;
    private Integer product_id;

    @JsonIgnore
    private RecipeInfo recipe;
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
        return recipe.getId();
    }

    public RecipeInfo getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeInfo recipe) {
        this.recipe = recipe;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
