package com.coffeegetaway.controller.order;

import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeMiniIngredientInfo;
import com.coffeegetaway.controller.house.ProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8081/recipe_ingredients/";

    @GetMapping("/{id}")
    public RecipeIngredientInfo recipeIngredientById(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        RecipeIngredientInfo result = restTemplate.getForObject(urlTarget, RecipeIngredientInfo.class);
        return result;
    }

    @GetMapping
    public List<RecipeIngredientInfo> allRecipeIngredients() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<RecipeIngredientInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecipeIngredientInfo>>(){});
        return result.getBody();
    }

    @DeleteMapping("/{id}")
    public void deleteRecipeIngredient(@PathVariable Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @PostMapping("/")
    public ResponseEntity<RecipeIngredientInfo> createRecipeIngredient(@RequestBody RecipeMiniIngredientInfo recipeIngredientInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<RecipeMiniIngredientInfo> request = new HttpEntity<>(recipeIngredientInfo);
        RecipeIngredientInfo result = restTemplate.postForObject(default_urlTarget, request, RecipeIngredientInfo.class);
        if (result == null)
            return new ResponseEntity<>((RecipeIngredientInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<RecipeIngredientInfo>(result, HttpStatus.CREATED);    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredientInfo> updateRecipeIngredient(@RequestBody RecipeMiniIngredientInfo recipeIngredient,
                                                                       @PathVariable Integer id) {

        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<RecipeMiniIngredientInfo> request = new HttpEntity<>(recipeIngredient);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RecipeIngredientInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, RecipeIngredientInfo.class);
        return result;
    }
}
