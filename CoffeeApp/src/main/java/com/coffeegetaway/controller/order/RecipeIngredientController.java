package com.coffeegetaway.controller.order;

import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.helpers.CoffeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8081/recipe_ingredients/";

    @GetMapping("/{id}")
    public RecipeIngredientInfo recipeIngredientById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
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
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET", logger);
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
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE", logger);
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
