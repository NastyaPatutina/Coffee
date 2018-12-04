package com.coffee.controller;

import com.coffee.entity.Storage;
import com.coffee.helpers.Builder;
import com.coffee.model.house.ProductInfo;
import com.coffee.model.house.storage.*;
import com.coffee.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/{id}")
    public StorageInfo StorageById(@PathVariable Integer id) {
        return storageService.findStorageById(id);
    }

    @GetMapping
    public List<StorageInfo> allStorages(@RequestParam("house_id") Optional<Integer> houseId) {
        if (houseId.isPresent()) {
            return storageService.findStorageForHouse(houseId.get());
        } else {
            return storageService.findAllStorage();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteStorage(@PathVariable Integer id) {
        storageService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<StorageInfo> createStorage(@RequestBody StorageMiniInfo storageInfo) {
        Storage savedStorage = storageService.save(storageInfo);

        return new ResponseEntity<StorageInfo>(Builder.buildStorageInfo(savedStorage), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageInfo> updateStorage(@RequestBody StorageMiniInfo storage, @PathVariable Integer id) {

        StorageInfo storageOptional = storageService.findStorageById(id);

        if (storageOptional == null)
            return ResponseEntity.notFound().build();

        storage.setId(id);

        Storage savedStorage = storageService.save(storage);

        return new ResponseEntity<StorageInfo>(Builder.buildStorageInfo(savedStorage), HttpStatus.OK);
    }
}
