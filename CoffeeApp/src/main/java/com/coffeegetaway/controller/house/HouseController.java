package com.coffeegetaway.controller.house;

import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import com.coffeegetaway.service.house.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/houses")
public class HouseController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private HouseService houseService;

    @GetMapping("/{id}")
    public HouseInfo houseById(@PathVariable Integer id) {
        return houseService.findHouseById(id);
    }

    @GetMapping("/{id}/recipes")
    public List<RecipeWithIngredientsInfo> HouseAvailableRecipes(@PathVariable Integer id) {
        return houseService.availableRecipesById(id);
    }

    @GetMapping
    public List<HouseInfo> allHouses() {
        return houseService.allHouses();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Integer id) {
        houseService.deleteHouse(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<HouseInfo> createHouse(@RequestBody HouseInfo houseInfo) {
        return houseService.createHouse(houseInfo);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<HouseInfo> updateHouse(@RequestBody HouseInfo houseInfo, @PathVariable Integer id) {
        return houseService.updateHouse(houseInfo, id);
    }
}
