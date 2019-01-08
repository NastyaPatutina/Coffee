package com.coffee.helpers;

import com.coffee.entity.Customer;
import com.coffee.model.customer.CustomerInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nonnull;

public class Builder {
    @Nonnull
    public static CustomerInfo buildCustomerInfo(Customer customer) {
        CustomerInfo info = new CustomerInfo();
        info.setId(customer.getId());
        info.setFirstName(customer.getFirstName());
        info.setLastName(customer.getLastName());
        info.setGender(customer.getGender());
        info.setEmail(customer.getEmail());
        info.setPhone(customer.getPhone());
        return info;
    }

    @Nonnull
    public static Customer buildCustomerByInfo(CustomerInfo customerInfo) {
        Customer customer = new Customer();
        customer.setFirstName(customerInfo.getFirstName());
        customer.setLastName(customerInfo.getLastName());
        customer.setGender(customerInfo.getGender());
        customer.setEmail(customerInfo.getEmail());
        customer.setPhone(customerInfo.getPhone());
        return customer;
    }
}
