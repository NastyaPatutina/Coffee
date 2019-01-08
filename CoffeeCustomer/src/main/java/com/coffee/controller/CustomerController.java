package com.coffee.controller;

import com.coffee.entity.Customer;
import com.coffee.helpers.Builder;
import com.coffee.model.customer.CustomerInfo;
import com.coffee.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/{id}")
    public CustomerInfo customerById(@PathVariable Integer id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping
    public List<CustomerInfo> allCustomers() {
        return customerService.findAllCustomers();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerInfo> createCustomer(@RequestBody CustomerInfo customerInfo) {
        Customer savedCustomer = customerService.save(customerInfo);

        return new ResponseEntity<CustomerInfo>(Builder.buildCustomerInfo(savedCustomer), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerInfo> updateCustomer(@RequestBody CustomerInfo customer, @PathVariable Integer id) {

        CustomerInfo customerOptional = customerService.findCustomerById(id);

        if (customerOptional == null) {
            logger.warn("Warning!!! No customer with id = " + id);
            return ResponseEntity.notFound().build();
        }

        customer.setId(id);

        Customer savedCustomer = customerService.save(customer);

        return new ResponseEntity<CustomerInfo>(Builder.buildCustomerInfo(savedCustomer), HttpStatus.OK);
    }
}
