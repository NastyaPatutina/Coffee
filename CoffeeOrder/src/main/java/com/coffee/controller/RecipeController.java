package com.coffee.controller;

import com.coffee.entity.Recipe;
import com.coffee.model.order.recipe.*;
import com.coffee.model.order.recipeIngredient.*;
import com.coffee.service.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

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
    public ResponseEntity<Object> createRecipe(@RequestBody RecipeInfo recipeInfo) {
        Recipe savedRecipe = recipeService.save(recipeInfo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRecipe.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@RequestBody RecipeInfo recipe, @PathVariable Integer id) {

        RecipeInfo recipeOptional = recipeService.findRecipeById(id);

        if (recipeOptional == null)
            return ResponseEntity.notFound().build();

        recipe.setId(id);

        recipeService.save(recipe);

        return ResponseEntity.noContent().build();
    }
}
