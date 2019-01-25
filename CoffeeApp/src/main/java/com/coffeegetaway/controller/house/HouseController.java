package com.coffeegetaway.controller.house;

import com.coffee.model.house.HouseInfo;
import com.coffeegetaway.service.house.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> HouseAvailableRecipes(@PathVariable Integer id) {
        return houseService.houseWithRecipesById(id);
    }

    @GetMapping
    public List<HouseInfo> allHouses() {
        return houseService.allHouses();
    }

    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Integer id) {
        houseService.deleteHouse(id);
    }

    @PostMapping("/")
    public ResponseEntity<HouseInfo> createHouse(@RequestBody HouseInfo houseInfo) {
        return houseService.createHouse(houseInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseInfo> updateHouse(@RequestBody HouseInfo houseInfo, @PathVariable Integer id) {
        return houseService.updateHouse(houseInfo, id);
    }
}
