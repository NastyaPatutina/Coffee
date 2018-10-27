package com.coffeegetaway.controller;

import com.coffee.model.RecipeInfo;
import com.coffeegetaway.helpers.CoffeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private String default_urlTarget = "http://localhost:8081/recipes/";

    @GetMapping("/{id}")
    public RecipeInfo recipeById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET");
        RecipeInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, RecipeInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public List<RecipeInfo> allRecipes() {
        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET");
        ObjectMapper objectMapper = new ObjectMapper();
        List<RecipeInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<RecipeInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE");
    }

    @PostMapping("/")
    public ResponseEntity<Object> createRecipe(@RequestBody RecipeInfo recipeInfo) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@RequestBody RecipeInfo recipe, @PathVariable Integer id) {
        return null;
    }
}
