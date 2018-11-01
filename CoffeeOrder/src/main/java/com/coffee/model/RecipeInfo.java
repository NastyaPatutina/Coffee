package com.coffee.model;

import com.coffee.entity.RecipeIngredient;

import java.util.Set;

public class RecipeInfo {
    private Integer id;
    private String name;
    private Integer cost;

    private Set<RecipeIngredientInfo> recipeIngredients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Set<RecipeIngredientInfo> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredientInfo> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
