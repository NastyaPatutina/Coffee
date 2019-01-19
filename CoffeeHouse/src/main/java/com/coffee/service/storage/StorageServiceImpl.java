package com.coffee.service.storage;

import com.coffee.entity.Product;
import com.coffee.entity.Storage;
import com.coffee.helpers.Builder;
import com.coffee.model.house.storage.*;
import com.coffee.repository.HouseRepository;
import com.coffee.repository.ProductRepository;
import com.coffee.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService{
    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ProductRepository productRepository;

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
        Storage storage;
        try {
            storage = storageRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Storage not found", ex);
        }
        return Builder.buildStorageInfo(storage);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        Storage storage;
        try {
            storage = storageRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Storage not found", ex);
        }

        storageRepository.delete(storage);
    }

    @Override
    @Transactional
    public Storage save(StorageMiniInfo storage) {
        Storage st;
        try {
            st = storageRepository.save(Builder.buildStorageByInfo(storage,
                    houseRepository.findById(storage.getHouseId()).orElse(null),
                    productRepository.findById(storage.getProductId()).orElse(null)));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Error save storage: " + ex.getCause().getCause().getMessage(), ex);
        }
        return st;
    }
}
