package com.coffee.model;

import com.coffee.entity.RecipeIngredient;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeInfo {
    private Integer id;
    private String name;
    private Integer cost;
    private List<RecipeIngredientInfo> recipeIngredients = new ArrayList<>();

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

    public List<RecipeIngredientInfo> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredientInfo> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
