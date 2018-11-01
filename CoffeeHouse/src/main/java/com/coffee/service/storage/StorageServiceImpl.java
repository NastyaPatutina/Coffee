package com.coffee.service.storage;

import com.coffee.entity.Storage;
import com.coffee.model.HouseInfo;
import com.coffee.model.StorageInfo;
import com.coffee.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService{
    @Autowired
    private StorageRepository storageRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<StorageInfo> findAllStorage() {
        return storageRepository.findAll()
                .stream()
                .map(this::buildStorageInfo)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public List<StorageInfo> findStorageForHouse(@Nonnull Integer houseId) {
        return storageRepository.findByHouseId(houseId)
                .stream()
                .map(this::buildStorageInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public StorageInfo findStorageById(@Nonnull Integer id) {
        return storageRepository.findById(id).map(this::buildStorageInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        storageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Storage save(StorageInfo storage) {
        return storageRepository.save(buildStorageByInfo(storage));
    }

    @Nonnull
    private StorageInfo buildStorageInfo(Storage storage) {
        StorageInfo info = new StorageInfo();
        info.setId(storage.getId());
        info.setCount(storage.getCount());
        info.setHouseId(storage.getHouseId());
        info.setProductId(storage.getProductId());
        return info;
    }

    @Nonnull
    private Storage buildStorageByInfo(StorageInfo storageInfo) {
        Storage storage = storageRepository.findById(storageInfo.getId()).orElse(null);
        storage.setId(storageInfo.getId());
        storage.setCount(storageInfo.getCount());
        storage.setHouseId(storageInfo.getHouseId());
        storage.setProductId(storageInfo.getProductId());
        return storage;
    }
}
