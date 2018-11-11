package com.coffee.service.order;

import com.coffee.entity.Order;
import com.coffee.model.OrderInfo;
import com.coffee.model.OrderMiniInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface OrderService {
    @Nonnull
    List<OrderInfo> findAllOrders();

    @Nonnull
    List<OrderInfo> findOrderByUserId(@Nonnull Integer userId);

    @Nonnull
    List<OrderInfo> findOrderByCoffeeHouseId(@Nonnull Integer coffeeHouseId);

    @Nullable
    OrderInfo findOrderById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    Order save(OrderMiniInfo orderInfo);
}
