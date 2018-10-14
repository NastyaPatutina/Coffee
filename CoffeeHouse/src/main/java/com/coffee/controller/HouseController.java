package com.coffee.controller;

import com.coffee.entity.House;
import com.coffee.model.HouseInfo;
import com.coffee.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Object> createHouse(@RequestBody House houseInfo) {
        House savedHouse = houseService.save(houseInfo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedHouse.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateHouse(@RequestBody House house, @PathVariable Integer id) {

        HouseInfo houseOptional = houseService.findHouseById(id);

        if (houseOptional != null)
            return ResponseEntity.notFound().build();

        house.setId(id);

        houseService.save(house);

        return ResponseEntity.noContent().build();
    }
}
