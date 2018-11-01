package com.coffeegetaway.controller.house;

import com.coffee.model.HouseInfo;
import com.coffee.model.RecipeInfo;
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
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        HouseInfo house = null;
        try {
            house = objectMapper.readValue(res_requst, HouseInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<RecipeInfo> res = null;

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
