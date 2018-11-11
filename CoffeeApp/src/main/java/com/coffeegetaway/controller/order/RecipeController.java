package com.coffeegetaway.controller.order;

import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
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
@RequestMapping("/recipes")
public class RecipeController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8081/recipes/";

    @GetMapping("/{id}")
    public RecipeWithIngredientsInfo recipeById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        RecipeWithIngredientsInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, RecipeWithIngredientsInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/{id}/ingredients")
    public List<OnlyIngredientInfo> recipeIngredientById(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString() + "/ingredients";
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        List<OnlyIngredientInfo>  res = null;
        try {
            res = objectMapper.readValue(res_requst,new TypeReference<List<OnlyIngredientInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    @GetMapping
    public List<RecipeWithIngredientsInfo> allRecipes() {
        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET", logger);
        ObjectMapper objectMapper = new ObjectMapper();
        List<RecipeWithIngredientsInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<RecipeWithIngredientsInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE", logger);
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
