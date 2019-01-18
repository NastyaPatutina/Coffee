package com.coffeegetaway.service.house;

import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import com.coffeegetaway.service.auth.Authorize;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {

    private String default_urlTarget = "http://localhost:8080/houses/";

    private Authorize auth = new Authorize("http://localhost:8080/auth", "getaway", "getaway-house");

    @Override
    public HouseInfo findHouseById(Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity(headers);

        String urlTarget = default_urlTarget + id.toString();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlTarget);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HouseInfo> result = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, request, HouseInfo.class);
        return result.getBody();
    }

    @Override
    public List<HouseInfo> allHouses() {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<HouseInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<HouseInfo>>(){});
        return result.getBody();
    }

    @Override
    public List<RecipeWithIngredientsInfo> availableRecipesById(Integer id) {

        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = "http://localhost:8080/storage/?house_id=" + id.toString();
        ResponseEntity<List<StorageInfo>> storage_requst = restTemplate.exchange(urlTarget, HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<StorageInfo>>(){});
        List<StorageInfo> storage = storage_requst.getBody();

        restTemplate = new RestTemplate();
        urlTarget = "http://localhost:8081/recipes/";
        ResponseEntity<List<RecipeWithIngredientsInfo>> ri_requst = restTemplate.exchange(urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecipeWithIngredientsInfo>>(){});
        List<RecipeWithIngredientsInfo> recipesInfo = ri_requst.getBody();

        return findAvailableRecipe(recipesInfo, storage);
    }

    @Override
    public void deleteHouse(Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        String urlTarget = default_urlTarget + id.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity( headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlTarget);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.DELETE,
                request,
                String.class);
    }

    @Override
    public ResponseEntity<HouseInfo> updateHouse(HouseInfo houseInfo, Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity<HouseInfo> request = new HttpEntity<>(houseInfo, headers);

        String urlTarget = default_urlTarget + id.toString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HouseInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, HouseInfo.class);
        return result;
    }

    @Override
    public ResponseEntity<HouseInfo> createHouse(HouseInfo houseInfo) {
        if (!auth.isAuthorze())
            auth.authorize();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity<HouseInfo> request = new HttpEntity<>(houseInfo, headers);

        HouseInfo result = restTemplate.postForObject(default_urlTarget, request, HouseInfo.class);
        if (result == null)
            return new ResponseEntity<>((HouseInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<HouseInfo>(result, HttpStatus.CREATED);
    }

    private List<RecipeWithIngredientsInfo> findAvailableRecipe(List<RecipeWithIngredientsInfo> recipesInfo, List<StorageInfo> storage) {
        List<RecipeWithIngredientsInfo> res = new ArrayList<>(recipesInfo);

        for (RecipeWithIngredientsInfo recipeInfo: recipesInfo) {
            for (OnlyIngredientInfo recipeIngredientInfo: recipeInfo.getRecipeIngredients()) {
                if (isProductInStorageList(storage, recipeIngredientInfo.getProductId())) {
                    if (!isIngredientAvailiable(storage, recipeIngredientInfo)) {
                        res.remove(recipeInfo);
                    }
                } else {
                    res.remove(recipeInfo);
                }
            }
        }
        return res;
    }

    private Boolean isIngredientAvailiable(List<StorageInfo> storage, OnlyIngredientInfo recipeIngredientInfo) {
        for (StorageInfo storageInfo : storage) {
            if (recipeIngredientInfo.getProductId() == storageInfo.getProduct().getId() &&
                    recipeIngredientInfo.getCount() > storageInfo.getCount()) {
                return false;
            }
        }
        return true;
    }

    private Boolean isProductInStorageList(List<StorageInfo> storage, Integer product_id) {
        for (StorageInfo storageInfo : storage) {
            if (product_id == storageInfo.getProduct().getId()) {
                return true;
            }
        }
        return false;
    }
}
