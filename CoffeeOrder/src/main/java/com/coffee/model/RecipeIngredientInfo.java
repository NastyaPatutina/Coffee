package com.coffee.model;


public class RecipeIngredientInfo extends OnlyIngredientInfo {
    private RecipeInfo recipe;

    public RecipeInfo getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeInfo recipe) {
        this.recipe = recipe;
    }
}
