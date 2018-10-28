package com.coffeegetaway.controller.house;

import com.coffee.model.StorageInfo;
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
@RequestMapping("/storage")
public class StorageController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);
    private String default_urlTarget = "http://localhost:8080/storage/";

    @GetMapping("/{id}")
    public StorageInfo StorageById(@PathVariable Integer id) {

        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String res_requst = CoffeeRequest.generate(urlTarget, urlParameters,"GET", logger);
        StorageInfo res = null;
        try {
            res = objectMapper.readValue(res_requst, StorageInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public List<StorageInfo> allStorages() {

        String urlParameters = "";
        String res_requst = CoffeeRequest.generate(default_urlTarget, urlParameters, "GET", logger);
        ObjectMapper objectMapper = new ObjectMapper();
        List<StorageInfo> res = null;
        try {
            res = objectMapper.readValue(res_requst, new TypeReference<List<StorageInfo>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @DeleteMapping("/{id}")
    public void deleteStorage(@PathVariable Integer id) {

        String urlParameters = "";
        String urlTarget = default_urlTarget + id.toString();
        CoffeeRequest.generate(urlTarget, urlParameters,"DELETE", logger);

    }

    @PostMapping("/")
    public ResponseEntity<Object> createStorage(@RequestBody StorageInfo storageInfo) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStorage(@RequestBody StorageInfo storage, @PathVariable Integer id) {
        return null;
    }
}
