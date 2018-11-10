package com.coffee.controller;

import com.coffee.entity.RecipeIngredient;
import com.coffee.model.RecipeIngredientInfo;
import com.coffee.service.recipeIngredient.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Object> createRecipeIngredient(@RequestBody RecipeIngredientInfo recipeIngredientInfo) {
        RecipeIngredient savedRecipeIngredient = recipeIngredientService.save(recipeIngredientInfo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRecipeIngredient.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipeIngredient(@RequestBody RecipeIngredientInfo recipeIngredient, @PathVariable Integer id) {

        RecipeIngredientInfo recipeIngredientOptional = recipeIngredientService.findRecipeIngredientById(id);

        if (recipeIngredientOptional == null)
            return ResponseEntity.notFound().build();

        recipeIngredient.setId(id);

        recipeIngredientService.save(recipeIngredient);

        return ResponseEntity.noContent().build();
    }
}
