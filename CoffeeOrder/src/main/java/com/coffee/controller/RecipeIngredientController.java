package com.coffee.controller;

import com.coffee.entity.RecipeIngredient;
import com.coffee.helpers.Builder;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipeIngredient.*;
import com.coffee.service.recipeIngredient.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {
    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @GetMapping("/{id}")
    public RecipeIngredientInfo recipeIngredientById(@PathVariable Integer id) {
        return recipeIngredientService.findRecipeIngredientById(id);
    }

    @GetMapping
    public List<RecipeIngredientInfo> allRecipeIngredients() {
        return recipeIngredientService.findAllRecipeIngredients();
    }

    @DeleteMapping("/{id}")
    public void deleteRecipeIngredient(@PathVariable Integer id) {
        recipeIngredientService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<RecipeIngredientInfo> createRecipeIngredient(@RequestBody RecipeMiniIngredientInfo recipeIngredientInfo) {
        RecipeIngredient savedRecipeIngredient = recipeIngredientService.save(recipeIngredientInfo);

        return new ResponseEntity<RecipeIngredientInfo>(Builder.buildRecipeIngredientInfo(savedRecipeIngredient), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredientInfo> updateRecipeIngredient(@RequestBody RecipeMiniIngredientInfo recipeIngredient,
                                                         @PathVariable Integer id) {

        RecipeIngredientInfo recipeIngredientOptional = recipeIngredientService.findRecipeIngredientById(id);

        if (recipeIngredientOptional == null)
            return ResponseEntity.notFound().build();

        recipeIngredient.setId(id);

        RecipeIngredient savedRecipeIngredient = recipeIngredientService.save(recipeIngredient);

        return new ResponseEntity<RecipeIngredientInfo>(Builder.buildRecipeIngredientInfo(savedRecipeIngredient), HttpStatus.OK);
    }
}
