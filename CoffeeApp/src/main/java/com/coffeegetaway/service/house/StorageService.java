package com.coffeegetaway.service.house;

import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.house.storage.StorageMiniInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface StorageService {
    StorageInfo findStorageById(Integer id);

    List<StorageInfo> allStorage(Optional<Integer> houseId);

    void deleteStorage(Integer id);

    ResponseEntity<StorageInfo> updateStorage(StorageMiniInfo storageInfo, Integer id);

    ResponseEntity<StorageInfo> createStorage(StorageMiniInfo storageInfo);
}
