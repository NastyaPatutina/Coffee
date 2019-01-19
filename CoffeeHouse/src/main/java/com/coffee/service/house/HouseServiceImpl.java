package com.coffee.service.house;

import com.coffee.entity.House;
import com.coffee.helpers.Builder;
import com.coffee.model.house.HouseInfo;
import com.coffee.repository.HouseRepository;
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
        House house;
        try {
            house = houseRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "House not found", ex);
        }
        return Builder.buildHouseInfo(house);
    }

    @Nullable
    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        House house;
        try {
            house = houseRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "House not found", ex);
        }

        houseRepository.delete(house);
    }

    @Override
    @Transactional
    public House save(HouseInfo house) {
        House savedHouse;
        try {
            savedHouse = houseRepository.save(Builder.buildHouseByInfo(house));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "Error save house: " + ex.getCause().getCause().getMessage(), ex);
        }
        return savedHouse;
    }
}
