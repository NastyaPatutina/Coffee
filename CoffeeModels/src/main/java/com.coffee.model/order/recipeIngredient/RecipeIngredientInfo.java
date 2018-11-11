package com.coffee.model.order.recipeIngredient;


import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;

public class RecipeIngredientInfo extends OnlyIngredientInfo {
    private RecipeInfo recipe;

    public RecipeInfo getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeInfo recipe) {
        this.recipe = recipe;
    }
}
