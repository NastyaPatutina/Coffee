package com.coffee.model.order.recipe;

import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;

import java.util.ArrayList;
import java.util.List;

public class RecipeWithIngredientsInfo extends RecipeInfo {
    private List<OnlyIngredientInfo> recipeIngredients = new ArrayList<>();

    public List<OnlyIngredientInfo> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<OnlyIngredientInfo> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
