package com.coffeegetaway.controller.order;


import com.coffee.model.RecipeIngredientInfo;
import com.coffeegetaway.helpers.CoffeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {

    private String default_urlTarget = "http://localhost:8081/recipe_ingredients/";

    @GetMapping("/{id}")
    public RecipeIngredientInfo recipeIngredientById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET");
        RecipeIngredientInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, RecipeIngredientInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public List<RecipeIngredientInfo> allRecipeIngredients() {
        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET");
        ObjectMapper objectMapper = new ObjectMapper();
        List<RecipeIngredientInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<RecipeIngredientInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteRecipeIngredient(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE");
    }

    @PostMapping("/")
    public ResponseEntity<Object> createRecipeIngredient(@RequestBody RecipeIngredientInfo recipeIngredientInfo) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipeIngredient(@RequestBody RecipeIngredientInfo recipeIngredient, @PathVariable Integer id) {
        return null;
    }
}
