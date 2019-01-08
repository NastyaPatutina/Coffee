package com.coffeegetaway.customer;


import com.coffee.model.helper.JsonMapper;
import com.coffee.model.customer.CustomerInfo;
import com.coffeegetaway.controller.customer.CustomerController;
import com.coffeegetaway.service.customer.CustomerService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    private static Logger logger = LoggerFactory.getLogger(CustomerControllerIntegrationTest.class);


    @Test
    public void getCustomers()
            throws Exception {

        CustomerInfo alex = new CustomerInfo();
        alex.setFirstName("alex");
        alex.setLastName("Alexeew");
        alex.setGender(CustomerInfo.GenderType.male);
        alex.setEmail("aalexeew@gmail.com");
        alex.setPhone("89090009900");


        CustomerInfo mike = new CustomerInfo();
        mike.setFirstName("Mike");
        mike.setLastName("Bolck");
        mike.setGender(CustomerInfo.GenderType.male);
        mike.setEmail("blmike@gmail.com");
        mike.setPhone("89009990099");
        List<CustomerInfo> allCustomers= Arrays.asList(alex, mike);

        given(service.allCustomers()).willReturn(allCustomers);

        mvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(alex.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(alex.getLastName())))
                .andExpect(jsonPath("$[0].gender", is(alex.getGender().toString())))
                .andExpect(jsonPath("$[0].email", is(alex.getEmail())))
                .andExpect(jsonPath("$[0].phone", is(alex.getPhone())))
                .andExpect(jsonPath("$[1].firstName", is(mike.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(mike.getLastName())))
                .andExpect(jsonPath("$[1].gender", is(mike.getGender().toString())))
                .andExpect(jsonPath("$[1].email", is(mike.getEmail())))
                .andExpect(jsonPath("$[1].phone", is(mike.getPhone())));
    }

    @Test
    public void getCustomer()
            throws Exception {

        CustomerInfo alex = new CustomerInfo();
        alex.setFirstName("alex");
        alex.setLastName("Alexeew");
        alex.setGender(CustomerInfo.GenderType.male);
        alex.setEmail("aalexeew@gmail.com");
        alex.setPhone("89090009900");

        given(service.findCustomerById(20)).willReturn(alex);

        mvc.perform(get("/customers/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(alex.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(alex.getLastName())))
                .andExpect(jsonPath("$.gender", is(alex.getGender().toString())))
                .andExpect(jsonPath("$.email", is(alex.getEmail())))
                .andExpect(jsonPath("$.phone", is(alex.getPhone())));
    }

    @Test
    public void deleteCustomer()
            throws Exception {
        mvc.perform(delete("/customers/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteCustomer(anyInt());
    }

    @Test
    public void createCustomer()
            throws Exception {

        CustomerInfo mike = new CustomerInfo("Mike",
                "Bolck",
                CustomerInfo.GenderType.male,
                "blmike@gmail.com",
                "89009990099");
        ResponseEntity<CustomerInfo> res = new ResponseEntity<CustomerInfo>(mike, HttpStatus.CREATED);
        given(service.createCustomer(refEq(mike))).willReturn(res);

        mvc.perform(post("/customers/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(mike))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createCustomer(refEq(mike));

    }

    @Test
    public void updateCustomer()
            throws Exception {

        CustomerInfo mikeInfo = new CustomerInfo("Mike",
                "Bolck",
                CustomerInfo.GenderType.male,
                "blmike@gmail.com",
                "89009990099");


        CustomerInfo mikeNewInfo = new CustomerInfo("MikeNew",
                "BlockNew",
                CustomerInfo.GenderType.male,
                "blmikeNew@gmail.com",
                "89009990100");


        mikeNewInfo.setId(21);

        ResponseEntity<CustomerInfo> res = new ResponseEntity<CustomerInfo>(mikeNewInfo, HttpStatus.CREATED);

        given(service.updateCustomer(refEq(mikeNewInfo), refEq(21))).willReturn(res);

        mvc.perform(put("/customers/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(mikeNewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).updateCustomer(refEq(mikeNewInfo), refEq(21));
    }
}
