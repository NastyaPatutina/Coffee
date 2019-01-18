package com.coffeegetaway.controller.order;

import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeMiniIngredientInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.service.order.RecipeIngredientService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @GetMapping("/{id}")
    public RecipeIngredientInfo recipeIngredientById(@PathVariable Integer id) {
        return recipeIngredientService.findRecipeIngredientById(id);
    }

    @GetMapping
    public List<RecipeIngredientInfo> allRecipeIngredients() {
        return recipeIngredientService.allRecipeIngredients();
    }

    @DeleteMapping("/{id}")
    public void deleteRecipeIngredient(@PathVariable Integer id) {
        recipeIngredientService.deleteRecipeIngredient(id);
    }

    @PostMapping("/")
    public ResponseEntity<RecipeIngredientInfo> createRecipeIngredient(@RequestBody RecipeMiniIngredientInfo recipeIngredientInfo) {
        return recipeIngredientService.createRecipeIngredient(recipeIngredientInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredientInfo> updateRecipeIngredient(@RequestBody RecipeMiniIngredientInfo recipeIngredient,
                                                                       @PathVariable Integer id) {
        return recipeIngredientService.updateRecipeIngredient(recipeIngredient, id);
    }
}
