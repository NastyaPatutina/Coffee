package com.coffee.service;

import com.coffee.entity.Customer;
import com.coffee.helpers.Builder;
import com.coffee.model.customer.*;
import com.coffee.repository.CustomerRepository;
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
        Customer customer;
        try {
            customer = customerRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found", ex);
        }

        return Builder.buildCustomerInfo(customer);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {

        Customer customer;
        try {
            customer = customerRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found", ex);
        }

        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public Customer save(CustomerInfo customerInfo) {

        Customer customer;
        try {
            customer = customerRepository.save(Builder.buildCustomerByInfo(customerInfo));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Error save customer: " + ex.getCause().getCause().getMessage(), ex);
        }

        return customer;
    }
}
