package com.coffeegetaway.controller.house;

import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8080/houses/";

    @GetMapping("/{id}")
    public HouseInfo houseById(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        HouseInfo result = restTemplate.getForObject(urlTarget, HouseInfo.class);
        return result;
    }

    @GetMapping("/{id}/available_recipes")
    public List<RecipeWithIngredientsInfo> HouseAvailableRecipes(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();

        String urlTarget = "http://localhost:8080/storage/?house_id=" + id.toString();

        ResponseEntity<List<StorageInfo>> storage_requst = restTemplate.exchange(urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StorageInfo>>(){});
        List<StorageInfo> storage = storage_requst.getBody();

        restTemplate = new RestTemplate();
        urlTarget = "http://localhost:8081/recipes/";

        ResponseEntity<List<RecipeWithIngredientsInfo>> ri_requst = restTemplate.exchange(urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecipeWithIngredientsInfo>>(){});

        List<RecipeWithIngredientsInfo> recipesInfo = ri_requst.getBody();

        List<RecipeWithIngredientsInfo> res = new ArrayList<>(recipesInfo);

        for (RecipeWithIngredientsInfo recipeInfo: recipesInfo) {
            for (OnlyIngredientInfo recipeIngredientInfo: recipeInfo.getRecipeIngredients()) {
                if (isProductInStorageList(storage, recipeIngredientInfo.getProductId())) {
                    for (StorageInfo storageInfo : storage) {
                        if (recipeIngredientInfo.getProductId() == storageInfo.getProduct().getId() &&
                                recipeIngredientInfo.getCount() > storageInfo.getCount()) {
                            res.remove(recipeInfo);
                        }
                    }
                } else {
                    res.remove(recipeInfo);
                }
            }
        }

        return res;
    }

    private Boolean isProductInStorageList(List<StorageInfo> storage, Integer product_id) {
        for (StorageInfo storageInfo : storage) {
            if (product_id == storageInfo.getProduct().getId()) {
                return true;
            }
        }
        return false;
    }

    @GetMapping
    public List<HouseInfo> allHouses() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<HouseInfo>> result = restTemplate.exchange(default_urlTarget, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<HouseInfo>>(){});
        return result.getBody();

    }

    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(urlTarget,  params );
    }

    @PostMapping("/")
    public ResponseEntity<HouseInfo> createHouse(@RequestBody HouseInfo houseInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<HouseInfo> request = new HttpEntity<>(houseInfo);
        HouseInfo result = restTemplate.postForObject(default_urlTarget, request, HouseInfo.class);
        if (result == null)
            return new ResponseEntity<>((HouseInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<HouseInfo>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseInfo> updateHouse(@RequestBody HouseInfo house, @PathVariable Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<HouseInfo> request = new HttpEntity<>(house);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HouseInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, HouseInfo.class);
        return result;

    }
}
