package com.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coffee.entity.Storage;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository
        extends JpaRepository<Storage, Integer> {
}
