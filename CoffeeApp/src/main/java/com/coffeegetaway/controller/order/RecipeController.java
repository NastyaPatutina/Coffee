package com.coffeegetaway.controller.order;

import com.coffee.model.house.ProductInfo;
import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipe.RecipeWithProducts;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeIngredientWithProductInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.service.order.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

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
        return recipeService.allRecipes();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<RecipeWithProducts> createRecipe(@RequestBody RecipeWithProducts recipeInfo) {
        return recipeService.createRecipe(recipeInfo);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<RecipeWithProducts> updateRecipe(@RequestBody RecipeWithProducts recipe, @PathVariable Integer id) {
        return recipeService.updateRecipe(recipe, id);
    }
}
