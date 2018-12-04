package com.coffee.service.house;

import com.coffee.entity.House;
import com.coffee.model.house.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

public interface HouseService {
    @Nonnull
    List<HouseInfo> findAllHouses();

    @Nullable
    HouseInfo findHouseById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    House save(HouseInfo house);
}
