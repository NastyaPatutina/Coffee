package com.coffee.helpers;

import com.coffee.entity.Order;
import com.coffee.entity.Recipe;
import com.coffee.entity.RecipeIngredient;
import com.coffee.model.order.order.*;
import com.coffee.model.order.recipe.*;
import com.coffee.model.order.recipeIngredient.*;
import com.coffee.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Builder {

//   ******************************** Recipe ********************************

    @Nonnull
    public static RecipeInfo buildRecipeInfo(Recipe recipe) {
        RecipeInfo info = new RecipeInfo();
        info.setId(recipe.getId());
        info.setName(recipe.getName());
        info.setCost(recipe.getCost());
        return info;
    }
    @Nonnull
    public static RecipeWithIngredientsInfo buildRecipeInfoWithIngredients(Recipe recipe) {
        RecipeWithIngredientsInfo info = new RecipeWithIngredientsInfo();
        info.setId(recipe.getId());
        info.setName(recipe.getName());
        info.setCost(recipe.getCost());
        ArrayList<OnlyIngredientInfo> riInfo = new ArrayList<>();
        for(RecipeIngredient ri: recipe.getRecipeIngredients()) {
            riInfo.add(buildOnlyIngredientInfo(ri, info));
        }
        info.setRecipeIngredients(riInfo);
        return info;
    }

    @Nonnull
    public static Recipe buildRecipeByInfo(RecipeInfo recipeInfo) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeInfo.getId());
        recipe.setName(recipeInfo.getName());
        recipe.setCost(recipeInfo.getCost());
        return recipe;
    }

    @Nonnull
    public static RecipeInfo buildRecipeInfo(RecipeWithIngredientsInfo recipeInfo) {
        RecipeInfo rInfo = new RecipeInfo();
        rInfo.setId(recipeInfo.getId());
        rInfo.setCost(recipeInfo.getCost());
        rInfo.setName(recipeInfo.getName());
        return rInfo;
    }

//   ******************************** Order ********************************

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
    public static OrderMiniInfo buildOrderMiniInfo(Order order) {
        OrderMiniInfo info = new OrderMiniInfo();
        info.setId(order.getId());
        info.setUserId(order.getUserId());
        info.setRecipeId(order.getRecipe().getId());
        info.setCoffeeHouseId(order.getCoffeeHouseId());
        return info;
    }


    @Nonnull
    public static Order buildOrderByInfo(OrderMiniInfo orderInfo, Recipe recipe) {
        Order order = new Order();
        order.setUserId(orderInfo.getUserId());
        order.setRecipe(recipe);
        order.setCoffeeHouseId(orderInfo.getCoffeeHouseId());
        return order;
    }


//   ******************************** Recipe Ingredient ********************************

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
    public static RecipeMiniIngredientInfo buildRecipeIngredientMiniInfo(RecipeIngredient recipeIngredient) {
        RecipeMiniIngredientInfo info = new RecipeMiniIngredientInfo();
        info.setId(recipeIngredient.getId());
        info.setProductId(recipeIngredient.getProductId());
        info.setRecipeId(recipeIngredient.getRecipe().getId());
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
    public static OnlyIngredientInfo buildOnlyIngredientInfo(RecipeIngredient recipeIngredient, RecipeInfo recipe) {
        OnlyIngredientInfo info = new OnlyIngredientInfo();
        info.setId(recipeIngredient.getId());
        info.setProductId(recipeIngredient.getProductId());
        info.setCount(recipeIngredient.getCount());
        return info;
    }

    @Nonnull
    public static RecipeIngredient buildRecipeIngredientByMiniInfo(RecipeMiniIngredientInfo recipeIngredientInfo, Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setProductId(recipeIngredientInfo.getProductId());
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setCount(recipeIngredientInfo.getCount());
        return recipeIngredient;
    }

    @Nonnull
    public static RecipeIngredient buildRecipeMiniIngredientInfo(OnlyIngredientInfo oiInfo, Recipe recipe) {
        RecipeIngredient recipeIngredient= new RecipeIngredient();
        recipeIngredient.setId(oiInfo.getId());
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setCount(oiInfo.getCount());
        recipeIngredient.setProductId(oiInfo.getProductId());
        return recipeIngredient;
    }
}
