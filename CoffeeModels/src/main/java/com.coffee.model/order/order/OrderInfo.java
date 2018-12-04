package com.coffee.model.order.order;

import com.coffee.model.order.recipe.RecipeInfo;

public class OrderInfo {
    private Integer id;
    private Integer user_id;
    private RecipeInfo recipe;
    private Integer coffee_house_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public RecipeInfo getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeInfo recipe) {
        this.recipe = recipe;
    }

    public Integer getCoffeeHouseId() {
        return coffee_house_id;
    }

    public void setCoffeeHouseId(Integer coffee_house_id) {
        this.coffee_house_id = coffee_house_id;
    }
}
