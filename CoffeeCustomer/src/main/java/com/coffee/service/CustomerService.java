package com.coffee.service;

import com.coffee.entity.Customer;
import com.coffee.model.customer.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface CustomerService {
    @Nonnull
    List<CustomerInfo> findAllCustomers();

    @Nullable
    CustomerInfo findCustomerById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    Customer save(CustomerInfo customerInfo);
}