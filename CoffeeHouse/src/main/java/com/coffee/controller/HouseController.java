package com.coffee.controller;

import com.coffee.model.HouseInfo;
import com.coffee.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

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
}
