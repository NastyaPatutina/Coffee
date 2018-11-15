package com.coffeegetaway.service.order;

import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeMiniIngredientInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeIngredientService {
    RecipeIngredientInfo findRecipeIngredientById(Integer id);

    List<RecipeIngredientInfo> allRecipeIngredients();

    void deleteRecipeIngredient(Integer id);

    ResponseEntity<RecipeIngredientInfo> updateRecipeIngredient(RecipeMiniIngredientInfo recipeIngredientInfo, Integer id);

    ResponseEntity<RecipeIngredientInfo> createRecipeIngredient(RecipeMiniIngredientInfo recipeIngredientInfo);
}
