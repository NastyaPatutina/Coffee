package com.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coffee.entity.House;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository
        extends JpaRepository<House, Integer> {
}
