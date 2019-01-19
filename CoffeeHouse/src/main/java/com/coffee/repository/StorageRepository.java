package com.coffee.repository;

import com.coffee.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coffee.entity.Storage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository
        extends JpaRepository<Storage, Integer> {
    List<Storage> findByHouse_Id(Integer id);
    List<Storage> findByProduct_Id(Integer id);
}
