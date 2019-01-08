package com.coffee;

import com.coffee.controller.CustomerController;
import com.coffee.entity.Customer;
import com.coffee.helpers.Builder;
import com.coffee.model.helper.JsonMapper;
import com.coffee.model.customer.*;
import com.coffee.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        Customer alex = new Customer("alex",
                "Alexeew",
                CustomerInfo.GenderType.male,
                "aalexeew@gmail.com",
                "89090009900");
        Customer mike = new Customer("Mike",
                "Bolck",
                CustomerInfo.GenderType.male,
                "blmike@gmail.com",
                "89009990099");

        List<CustomerInfo> allCustomers= Arrays.asList(Builder.buildCustomerInfo(alex), Builder.buildCustomerInfo(mike));

        given(service.findAllCustomers()).willReturn(allCustomers);

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

        Customer alex = new Customer("alex",
                "Alexeew",
                CustomerInfo.GenderType.male,
                "aalexeew@gmail.com",
                "89090009900");

        CustomerInfo customerInfo = Builder.buildCustomerInfo(alex);

        given(service.findCustomerById(20)).willReturn(customerInfo);

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
        verify(service, times(1)).deleteById(anyInt());
    }

    @Test
    public void createCustomer()
            throws Exception {

        CustomerInfo mike = new CustomerInfo("Mike",
                "Bolck",
                CustomerInfo.GenderType.male,
                "blmike@gmail.com",
                "89009990099");
        given(service.save(refEq(mike))).willReturn(Builder.buildCustomerByInfo(mike));


        mvc.perform(post("/customers/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(mike))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(mike));

    }

    @Test
    public void updateCustomer()
            throws Exception {

        Customer mike = new Customer("Mike",
                "Bolck",
                CustomerInfo.GenderType.male,
                "blmike@gmail.com",
                "89009990099");

        CustomerInfo mikeInfo = Builder.buildCustomerInfo(mike);

        Customer mike_new = new Customer("MikeNew",
                "BlockNew",
                CustomerInfo.GenderType.male,
                "blmikeNew@gmail.com",
                "89009990100");

        CustomerInfo mikeNewInfo = Builder.buildCustomerInfo(mike_new);

        mikeNewInfo.setId(21);
        when(service.findCustomerById(21)).thenReturn(mikeInfo);
        given(service.save(refEq(mikeNewInfo))).willReturn(mike_new);


        mvc.perform(put("/customers/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(mikeNewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).save(refEq(mikeNewInfo));
    }
}
