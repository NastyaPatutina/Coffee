package com.coffee.controller;

import com.coffee.entity.Recipe;
import com.coffee.entity.RecipeIngredient;
import com.coffee.helpers.Builder;
import com.coffee.model.order.recipe.*;
import com.coffee.model.order.recipeIngredient.*;
import com.coffee.service.recipe.RecipeService;
import com.coffee.service.recipeIngredient.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @GetMapping("/{id}")
    public RecipeWithIngredientsInfo recipeById(@PathVariable Integer id) {
        return recipeService.findRecipeById(id);
    }

    @GetMapping("/{id}/ingredients")
    public List<OnlyIngredientInfo> recipeIngredientById(@PathVariable Integer id) {
        return recipeService.findRecipeById(id).getRecipeIngredients();
    }

    @GetMapping
    public List<RecipeWithIngredientsInfo> allRecipes() {
        return recipeService.findAllRecipes();
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<RecipeWithIngredientsInfo> createRecipe(@RequestBody RecipeWithIngredientsInfo recipeInfo) {
        Recipe savedRecipe = recipeService.save(Builder.buildRecipeByInfo(recipeInfo));

        for (OnlyIngredientInfo oiInfo : recipeInfo.getRecipeIngredients()) {
            savedRecipe.addRecipeIngredients(recipeIngredientService.save(Builder.buildRecipeMiniIngredientInfo(oiInfo, savedRecipe)));
        }

        return new ResponseEntity<RecipeWithIngredientsInfo>(Builder.buildRecipeInfoWithIngredients(savedRecipe), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeWithIngredientsInfo> updateRecipe(@RequestBody RecipeInfo recipe, @PathVariable Integer id) {

        RecipeInfo recipeOptional = recipeService.findRecipeById(id);

        if (recipeOptional == null)
            return ResponseEntity.notFound().build();

        recipe.setId(id);

        Recipe savedRecipe = recipeService.save(Builder.buildRecipeByInfo(recipe));

        return new ResponseEntity<RecipeWithIngredientsInfo>(Builder.buildRecipeInfoWithIngredients(savedRecipe), HttpStatus.OK);
    }
}
