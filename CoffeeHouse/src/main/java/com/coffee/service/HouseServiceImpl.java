package com.coffee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffee.repository.HouseRepository;
import com.coffee.entity.House;
import com.coffee.model.HouseInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<HouseInfo> findAllHouses() {
        return houseRepository.findAll()
                .stream()
                .map(this::buildHouseInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public HouseInfo findHouseById(@Nonnull Integer id) {
        return houseRepository.findById(id).map(this::buildHouseInfo).orElse(null);
    }

    @Nonnull
    private HouseInfo buildHouseInfo(House house) {
        HouseInfo info = new HouseInfo();
        info.setName(house.getName());
        info.setAddress(house.getAddress());
        info.setLongitude(house.getLongitude());
        info.setLatitude(house.getLatitude());
        return info;
    }
}