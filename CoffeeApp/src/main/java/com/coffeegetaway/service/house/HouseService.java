package com.coffeegetaway.service.house;

import com.coffee.model.house.HouseInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HouseService {
    HouseInfo findHouseById(Integer id);

    List<HouseInfo> allHouses();
    ResponseEntity<?> houseWithRecipesById(Integer id);

    List<RecipeWithIngredientsInfo> availableRecipesById(Integer id);

    void deleteHouse(Integer id);

    ResponseEntity<HouseInfo> updateHouse(HouseInfo houseInfo, Integer id);

    ResponseEntity<HouseInfo> createHouse(HouseInfo houseInfo);
}
