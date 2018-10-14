package com.coffee.service.storage;

import com.coffee.entity.Storage;
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
    public Storage save(Storage Storage) {
        return storageRepository.save(Storage);
    }

    @Nonnull
    private StorageInfo buildStorageInfo(Storage storage) {
        StorageInfo info = new StorageInfo();
        info.setId(storage.getId());
        info.setCount(storage.getCount());
        info.setHouse_id(storage.getHouse_id());
        info.setProduct_id(storage.getProduct_id());
        return info;
    }
}
