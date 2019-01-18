package com.coffeegetaway.controller.customer;

import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.customer.CustomerInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.service.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerInfo customerById(@PathVariable Integer id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/{id}/orders")
    public List<OrderInfo> customerOrdersById(@PathVariable Integer id) {
        return customerService.customerOrdersById(id);
    }

    @GetMapping
    public List<CustomerInfo> allCustomers() {
        return customerService.allCustomers();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerInfo> createCustomer(@RequestBody CustomerInfo customerInfo) {
        return customerService.createCustomer(customerInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerInfo> updateCustomer(@RequestBody CustomerInfo customer, @PathVariable Integer id) {
        return  customerService.updateCustomer(customer, id);
    }
}
