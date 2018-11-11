package com.coffee.service.house;

import com.coffee.entity.House;
import com.coffee.helpers.Builder;
import com.coffee.model.house.HouseInfo;
import com.coffee.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .map(Builder::buildHouseInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public HouseInfo findHouseById(@Nonnull Integer id) {
        return houseRepository.findById(id).map(Builder::buildHouseInfo).orElse(null);
    }

    @Nullable
    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        houseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public House save(HouseInfo house) {
        return houseRepository.save(Builder.buildHouseByInfo(house));
    }
}
