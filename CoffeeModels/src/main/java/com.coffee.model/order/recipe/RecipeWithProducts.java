package com.coffee.model.order.recipe;

import com.coffee.model.order.recipeIngredient.RecipeIngredientWithProductInfo;

import java.util.ArrayList;
import java.util.List;

public class RecipeWithProducts extends RecipeInfo {
    private List<RecipeIngredientWithProductInfo> recipeIngredients = new ArrayList<>();

    public List<RecipeIngredientWithProductInfo> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredientWithProductInfo> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
