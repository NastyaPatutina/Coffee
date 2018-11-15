package com.coffeegetaway.user;


import com.coffee.model.helper.JsonMapper;
import com.coffee.model.user.UserInfo;
import com.coffeegetaway.controller.user.UserController;
import com.coffeegetaway.service.user.UserService;
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
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private static Logger logger = LoggerFactory.getLogger(UserControllerIntegrationTest.class);


    @Test
    public void getUsers()
            throws Exception {

        UserInfo alex = new UserInfo();
        alex.setFirstName("alex");
        alex.setLastName("Alexeew");
        alex.setGender(UserInfo.GenderType.male);
        alex.setEmail("aalexeew@gmail.com");
        alex.setPhone("89090009900");


        UserInfo mike = new UserInfo();
        mike.setFirstName("Mike");
        mike.setLastName("Bolck");
        mike.setGender(UserInfo.GenderType.male);
        mike.setEmail("blmike@gmail.com");
        mike.setPhone("89009990099");
        List<UserInfo> allUsers= Arrays.asList(alex, mike);

        given(service.allUsers()).willReturn(allUsers);

        mvc.perform(get("/users")
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
    public void getUser()
            throws Exception {

        UserInfo alex = new UserInfo();
        alex.setFirstName("alex");
        alex.setLastName("Alexeew");
        alex.setGender(UserInfo.GenderType.male);
        alex.setEmail("aalexeew@gmail.com");
        alex.setPhone("89090009900");

        given(service.findUserById(20)).willReturn(alex);

        mvc.perform(get("/users/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(alex.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(alex.getLastName())))
                .andExpect(jsonPath("$.gender", is(alex.getGender().toString())))
                .andExpect(jsonPath("$.email", is(alex.getEmail())))
                .andExpect(jsonPath("$.phone", is(alex.getPhone())));
    }

    @Test
    public void deleteUser()
            throws Exception {
        mvc.perform(delete("/users/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteUser(anyInt());
    }

    @Test
    public void createUser()
            throws Exception {

        UserInfo mike = new UserInfo("Mike",
                "Bolck",
                UserInfo.GenderType.male,
                "blmike@gmail.com",
                "89009990099");
        ResponseEntity<UserInfo> res = new ResponseEntity<UserInfo>(mike, HttpStatus.CREATED);
        given(service.createUser(refEq(mike))).willReturn(res);

        mvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(mike))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createUser(refEq(mike));

    }

    @Test
    public void updateUser()
            throws Exception {

        UserInfo mikeInfo = new UserInfo("Mike",
                "Bolck",
                UserInfo.GenderType.male,
                "blmike@gmail.com",
                "89009990099");


        UserInfo mikeNewInfo = new UserInfo("MikeNew",
                "BlockNew",
                UserInfo.GenderType.male,
                "blmikeNew@gmail.com",
                "89009990100");


        mikeNewInfo.setId(21);

        ResponseEntity<UserInfo> res = new ResponseEntity<UserInfo>(mikeNewInfo, HttpStatus.CREATED);

        given(service.updateUser(refEq(mikeNewInfo), refEq(21))).willReturn(res);

        mvc.perform(put("/users/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(mikeNewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).updateUser(refEq(mikeNewInfo), refEq(21));
    }
}
