package com.coffeegetaway.service.customer;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.customer.CustomerInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    CustomerInfo findCustomerById(Integer id);

    List<OrderInfo> customerOrdersById(Integer id);

    List<CustomerInfo> allCustomers();

    void deleteCustomer(Integer id);

    ResponseEntity<CustomerInfo> updateCustomer(CustomerInfo customerInfo, Integer id);

    ResponseEntity<CustomerInfo> createCustomer(CustomerInfo customerInfo);
}
