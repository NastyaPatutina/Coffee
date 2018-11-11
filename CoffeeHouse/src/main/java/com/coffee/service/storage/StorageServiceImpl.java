package com.coffee.service.storage;

import com.coffee.entity.Storage;
import com.coffee.helpers.Builder;
import com.coffee.model.StorageInfo;
import com.coffee.model.StorageMiniInfo;
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
                .map(Builder::buildStorageInfo)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public List<StorageInfo> findStorageForHouse(@Nonnull Integer houseId) {
        return storageRepository.findByHouse_Id(houseId)
                .stream()
                .map(Builder::buildStorageInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public StorageInfo findStorageById(@Nonnull Integer id) {
        return storageRepository.findById(id).map(Builder::buildStorageInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        storageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Storage save(StorageMiniInfo storage) {
        return storageRepository.save(Builder.buildStorageByInfo(storage));
    }
}
