package com.coffee.model.order.order;

public class OrderMiniInfo {
    private Integer id;
    private Integer customer_id;
    private Integer recipe_id;
    private Integer coffee_house_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(Integer user_id) {
        this.customer_id = user_id;
    }

    public Integer getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Integer getCoffeeHouseId() {
        return coffee_house_id;
    }

    public void setCoffeeHouseId(Integer coffee_house_id) {
        this.coffee_house_id = coffee_house_id;
    }
}
