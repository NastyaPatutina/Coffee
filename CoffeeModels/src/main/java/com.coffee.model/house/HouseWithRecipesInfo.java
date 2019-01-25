package com.coffee.model.house;

import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;

import java.util.List;

public class HouseWithRecipesInfo extends HouseInfo {
    public List<RecipeWithIngredientsInfo> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeWithIngredientsInfo> recipes) {
        this.recipes = recipes;
    }

    private List<RecipeWithIngredientsInfo> recipes;
}
