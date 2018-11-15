package com.coffeegetaway.service.order;

import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeMiniIngredientInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeIngredientServiceImpl implements  RecipeIngredientService {

    private String default_urlTarget = "http://localhost:8081/recipe_ingredients/";

    @Override
    public RecipeIngredientInfo findRecipeIngredientById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        RecipeIngredientInfo result = restTemplate.getForObject(urlTarget, RecipeIngredientInfo.class);
        return result;
    }

    @Override
    public List<RecipeIngredientInfo> allRecipeIngredients() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<RecipeIngredientInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecipeIngredientInfo>>(){});
        return result.getBody();
    }

    @Override
    public void deleteRecipeIngredient(Integer id) {

        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @Override
    public ResponseEntity<RecipeIngredientInfo> updateRecipeIngredient(RecipeMiniIngredientInfo recipeIngredientInfo, Integer id) {

        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<RecipeMiniIngredientInfo> request = new HttpEntity<>(recipeIngredientInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RecipeIngredientInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, RecipeIngredientInfo.class);
        return result;
    }

    @Override
    public ResponseEntity<RecipeIngredientInfo> createRecipeIngredient(RecipeMiniIngredientInfo recipeIngredientInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<RecipeMiniIngredientInfo> request = new HttpEntity<>(recipeIngredientInfo);
        RecipeIngredientInfo result = restTemplate.postForObject(default_urlTarget, request, RecipeIngredientInfo.class);
        if (result == null)
            return new ResponseEntity<>((RecipeIngredientInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<RecipeIngredientInfo>(result, HttpStatus.CREATED);
    }
}
