package com.coffee.service.order;

import com.coffee.entity.Order;
import com.coffee.model.OrderInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface OrderService {
    @Nonnull
    List<OrderInfo> findAllOrders();

    @Nullable
    OrderInfo findOrderById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    Order save(OrderInfo orderInfo);
}
