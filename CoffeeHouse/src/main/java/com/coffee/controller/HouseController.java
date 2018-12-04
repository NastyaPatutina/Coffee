package com.coffee.controller;

import com.coffee.entity.House;

import com.coffee.entity.Product;
import com.coffee.helpers.Builder;
import com.coffee.model.house.*;
import com.coffee.service.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/{id}")
    public HouseInfo houseById(@PathVariable Integer id) {
        return houseService.findHouseById(id);
    }

    @GetMapping
    public List<HouseInfo> allHouses() {
        return houseService.findAllHouses();
    }

    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Integer id) {
        houseService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<HouseInfo> createHouse(@RequestBody HouseInfo houseInfo) {
        House savedHouse = houseService.save(houseInfo);

        return new ResponseEntity<HouseInfo>(Builder.buildHouseInfo(savedHouse), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseInfo> updateHouse(@RequestBody HouseInfo house, @PathVariable Integer id) {

        HouseInfo houseOptional = houseService.findHouseById(id);

        if (houseOptional == null)
            return ResponseEntity.notFound().build();

        house.setId(id);

        House savedHouse = houseService.save(house);

        return new ResponseEntity<HouseInfo>(Builder.buildHouseInfo(savedHouse), HttpStatus.OK);
    }
}
