package com.coffeegetaway.controller.order;

import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
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
@RequestMapping("/recipes")
public class RecipeController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8081/recipes/";

    @GetMapping("/{id}")
    public RecipeWithIngredientsInfo recipeById(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        RecipeWithIngredientsInfo result = restTemplate.getForObject(urlTarget, RecipeWithIngredientsInfo.class);
        return result;
    }

    @GetMapping("/{id}/ingredients")
    public List<OnlyIngredientInfo> recipeIngredientById(@PathVariable Integer id) {
        String urlTarget = default_urlTarget + id.toString() + "/ingredients";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<OnlyIngredientInfo>> result = restTemplate.exchange(urlTarget,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OnlyIngredientInfo>>(){});
        return result.getBody();
    }


    @GetMapping
    public List<RecipeWithIngredientsInfo> allRecipes() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<RecipeWithIngredientsInfo>> result = restTemplate.exchange(default_urlTarget,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecipeWithIngredientsInfo>>(){});
        return result.getBody();
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @PostMapping("/")
    public ResponseEntity<RecipeWithIngredientsInfo> createRecipe(@RequestBody RecipeInfo recipeInfo) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<RecipeInfo> request = new HttpEntity<>(recipeInfo);
        RecipeWithIngredientsInfo result = restTemplate.postForObject(default_urlTarget, request, RecipeWithIngredientsInfo.class);
        if (result == null)
            return new ResponseEntity<>((RecipeWithIngredientsInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<RecipeWithIngredientsInfo>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeWithIngredientsInfo> updateRecipe(@RequestBody RecipeInfo recipe, @PathVariable Integer id) {

        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<RecipeInfo> request = new HttpEntity<>(recipe);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RecipeWithIngredientsInfo> result = restTemplate.exchange(urlTarget,
                HttpMethod.PUT,
                request,
                RecipeWithIngredientsInfo.class);
        return result;
    }
}
