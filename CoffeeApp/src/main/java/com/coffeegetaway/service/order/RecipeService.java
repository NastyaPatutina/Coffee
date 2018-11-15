package com.coffeegetaway.service.order;

import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipe.RecipeWithProducts;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeService {
    RecipeWithIngredientsInfo findRecipeById(Integer id);

    List<RecipeWithIngredientsInfo> allRecipes();

    void deleteRecipe(Integer id);

    ResponseEntity<RecipeWithIngredientsInfo> updateRecipe(RecipeInfo recipeInfo, Integer id);

    ResponseEntity<RecipeWithProducts> createRecipe(RecipeWithProducts recipeInfo);
}
