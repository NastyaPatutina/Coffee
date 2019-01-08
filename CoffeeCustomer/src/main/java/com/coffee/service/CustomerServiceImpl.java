package com.coffee.service;

import com.coffee.entity.Customer;
import com.coffee.helpers.Builder;
import com.coffee.model.customer.*;
import com.coffee.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<CustomerInfo> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(Builder::buildCustomerInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public CustomerInfo findCustomerById(@Nonnull Integer id) {
        return customerRepository.findById(id).map(Builder::buildCustomerInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Customer save(CustomerInfo customerInfo) {
        return customerRepository.save(Builder.buildCustomerByInfo(customerInfo));
    }
}
