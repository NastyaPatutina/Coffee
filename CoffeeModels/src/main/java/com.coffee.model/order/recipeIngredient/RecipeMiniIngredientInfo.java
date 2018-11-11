package com.coffee.model.order.recipeIngredient;

import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;

public class RecipeMiniIngredientInfo extends OnlyIngredientInfo {
    private Integer recipe_id;

    public Integer getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }
}
