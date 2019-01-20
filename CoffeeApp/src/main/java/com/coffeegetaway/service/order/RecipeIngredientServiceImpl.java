package com.coffeegetaway.service.order;

import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeMiniIngredientInfo;
import com.coffeegetaway.ErrorModel;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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

        RecipeIngredientInfo result;
        try {
            result = restTemplate.getForObject(urlTarget, RecipeIngredientInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return result;
    }

    @Override
    public List<RecipeIngredientInfo> allRecipeIngredients() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<RecipeIngredientInfo>> result;
        try {
            result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RecipeIngredientInfo>>(){});
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return result.getBody();
    }

    @Override
    public void deleteRecipeIngredient(Integer id) {

        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete (urlTarget,  params );
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
    }

    @Override
    public ResponseEntity<RecipeIngredientInfo> updateRecipeIngredient(RecipeMiniIngredientInfo recipeIngredientInfo, Integer id) {

        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<RecipeMiniIngredientInfo> request = new HttpEntity<>(recipeIngredientInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RecipeIngredientInfo> result;
        try {
            result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, RecipeIngredientInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return result;
    }

    @Override
    public ResponseEntity<RecipeIngredientInfo> createRecipeIngredient(RecipeMiniIngredientInfo recipeIngredientInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<RecipeMiniIngredientInfo> request = new HttpEntity<>(recipeIngredientInfo);
        RecipeIngredientInfo result;
        try {
            result = restTemplate.postForObject(default_urlTarget, request, RecipeIngredientInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        }
        return new ResponseEntity<RecipeIngredientInfo>(result, HttpStatus.CREATED);
    }
}
