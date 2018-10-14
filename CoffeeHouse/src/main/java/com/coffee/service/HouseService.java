package com.coffee.service;

import com.coffee.model.HouseInfo;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

public interface HouseService {
    @Nonnull
    List<HouseInfo> findAllHouses();

    @Nullable
    HouseInfo findHouseById(@Nonnull Integer id);
}
