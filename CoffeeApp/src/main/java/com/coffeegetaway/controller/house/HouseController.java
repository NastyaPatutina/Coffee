package com.coffeegetaway.controller.house;

import com.coffee.model.HouseInfo;
import com.coffee.model.RecipeInfo;
import com.coffee.model.RecipeIngredientInfo;
import com.coffee.model.StorageInfo;
import com.coffee.repository.RecipeIngredientRepository;
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
@RequestMapping("/houses")
public class HouseController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private String default_urlTarget = "http://localhost:8080/houses/";

    @GetMapping("/{id}")
    public HouseInfo houseById(@PathVariable Integer id) {

        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        HouseInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, HouseInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/{id}/available_recipes")
    public List<RecipeInfo> HouseAvailableRecipes(@PathVariable Integer id) {

        String urlParameters = "";
        String urlTarget = "http://localhost:8080/storage/?house_id=" + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        List<StorageInfo> storage = null;
        try {
            storage = objectMapper.readValue(res_requst, new TypeReference<List<StorageInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        urlParameters = "";
        res_requst = CoffeeRequest.generate("http://localhost:8081/recipes/", urlParameters, "GET", logger);
        objectMapper = new ObjectMapper();
        List<RecipeInfo> recipesInfo = null;
        try {
            recipesInfo = objectMapper.readValue(res_requst, new TypeReference<List<RecipeInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<RecipeInfo> res = recipesInfo;
        for (RecipeInfo recipeInfo: recipesInfo) {
            urlParameters = "";
            res_requst = CoffeeRequest.generate("http://localhost:8081/recipes/" + recipeInfo.getId() + "/ingredients", urlParameters, "GET", logger);
            objectMapper = new ObjectMapper();
            List<RecipeIngredientInfo> recipeIngredientsInfo = null;
            try {
                recipeIngredientsInfo = objectMapper.readValue(res_requst, new TypeReference<List<RecipeIngredientInfo>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (RecipeIngredientInfo recipeIngredientInfo: recipeIngredientsInfo) {
                for (StorageInfo storageInfo: storage) {
                    if (recipeIngredientInfo.getProductId() == storageInfo.getProductId() && recipeIngredientInfo.getCount() < storageInfo.getCount()) {
                        res.remove(recipeInfo);
                    }
                }
            }
        }

        return res;
    }

    @GetMapping
    public List<HouseInfo> allHouses() {

        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET", logger);
        ObjectMapper objectMapper = new ObjectMapper();
        List<HouseInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<HouseInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }

    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Integer id) {
        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE", logger);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createHouse(@RequestBody HouseInfo houseInfo) {
        return null;

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateHouse(@RequestBody HouseInfo house, @PathVariable Integer id) {

        return null;

    }
}
