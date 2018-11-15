package com.coffeegetaway.controller.house;

import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.house.storage.StorageMiniInfo;
import com.coffeegetaway.service.house.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private static Logger logger = LoggerFactory.getLogger(StorageController.class);

    @Autowired
    private StorageService storageService;

    @GetMapping("/{id}")
    public StorageInfo storageById(@PathVariable Integer id) {
        return storageService.findStorageById(id);
    }

    @GetMapping
    public List<StorageInfo> allStorages(@RequestParam("house_id") Optional<Integer> houseId) {
        return storageService.allStorage(houseId);
    }

    @DeleteMapping("/{id}")
    public void deleteStorage(@PathVariable Integer id) {
        storageService.deleteStorage(id);
    }

    @PostMapping("/")
    public ResponseEntity<StorageInfo> createStorage(@RequestBody StorageMiniInfo storageInfo) {
        return storageService.createStorage(storageInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageInfo> updateStorage(@RequestBody StorageMiniInfo storageInfo, @PathVariable Integer id){
        return storageService.updateStorage(storageInfo, id);
    }
}
