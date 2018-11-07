package com.coffee.helpers;

import com.coffee.entity.Order;
import com.coffee.entity.Recipe;
import com.coffee.entity.RecipeIngredient;
import com.coffee.model.OrderInfo;
import com.coffee.model.RecipeInfo;
import com.coffee.model.RecipeIngredientInfo;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Builder {

    @Nonnull
    public static RecipeInfo buildRecipeInfo(Recipe recipe) {
        RecipeInfo info = new RecipeInfo();
        info.setId(recipe.getId());
        info.setName(recipe.getName());
        info.setCost(recipe.getCost());
        ArrayList<RecipeIngredientInfo> riInfo = new ArrayList<>();
        for(RecipeIngredient ri: recipe.getRecipeIngredients()) {
            riInfo.add(buildRecipeIngredientInfo(ri, info));
        }
        info.setRecipeIngredients(riInfo);
        return info;
    }

    @Nonnull
    public static Recipe buildRecipeByInfo(RecipeInfo recipeInfo) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeInfo.getName());
        recipe.setCost(recipeInfo.getCost());
        return recipe;
    }

    @Nonnull
    public static OrderInfo buildOrderInfo(Order order) {
        OrderInfo info = new OrderInfo();
        info.setId(order.getId());
        info.setUserId(order.getUserId());
        info.setRecipe(buildRecipeInfo(order.getRecipe()));
        info.setCoffeeHouseId(order.getCoffeeHouseId());
        return info;
    }

    @Nonnull
    public static Order buildOrderByInfo(OrderInfo orderInfo) {
        Order order = new Order();
        order.setUserId(orderInfo.getUserId());
        order.setRecipe(buildRecipeByInfo(orderInfo.getRecipe()));
        order.setCoffeeHouseId(orderInfo.getCoffeeHouseId());
        return order;
    }

    @Nonnull
    public static RecipeIngredientInfo buildRecipeIngredientInfo(RecipeIngredient recipeIngredient) {
        RecipeIngredientInfo info = new RecipeIngredientInfo();
        info.setId(recipeIngredient.getId());
        info.setProductId(recipeIngredient.getProductId());
        info.setRecipe(buildRecipeInfo(recipeIngredient.getRecipe()));
        info.setCount(recipeIngredient.getCount());
        return info;
    }
    @Nonnull
    public static RecipeIngredientInfo buildRecipeIngredientInfo(RecipeIngredient recipeIngredient, RecipeInfo recipe) {
        RecipeIngredientInfo info = new RecipeIngredientInfo();
        info.setId(recipeIngredient.getId());
        info.setProductId(recipeIngredient.getProductId());
        info.setRecipe(recipe);
        info.setCount(recipeIngredient.getCount());
        return info;
    }

    @Nonnull
    public static RecipeIngredient buildRecipeIngredientByInfo(RecipeIngredientInfo recipeIngredientInfo) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setProductId(recipeIngredientInfo.getProductId());
        recipeIngredient.setRecipe(buildRecipeByInfo(recipeIngredientInfo.getRecipe()));
        recipeIngredient.setCount(recipeIngredientInfo.getCount());
        return recipeIngredient;
    }
}