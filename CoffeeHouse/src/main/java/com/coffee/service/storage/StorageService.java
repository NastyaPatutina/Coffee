package com.coffee.service.storage;

import com.coffee.entity.Storage;
import com.coffee.model.HouseInfo;
import com.coffee.model.StorageInfo;
import com.coffee.model.StorageMiniInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface StorageService {
    @Nonnull
    List<StorageInfo> findAllStorage();

    @Nonnull
    List<StorageInfo> findStorageForHouse(@Nonnull Integer houseId);

    @Nullable
    StorageInfo findStorageById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    Storage save(StorageMiniInfo product);
}
