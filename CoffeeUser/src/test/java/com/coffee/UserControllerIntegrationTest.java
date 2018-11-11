package com.coffee;

import com.coffee.controller.UserController;
import com.coffee.entity.User;
import com.coffee.helpers.Builder;
import com.coffee.model.UserInfo;
import com.coffee.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.annotation.Nonnull;
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

        User alex = new User("alex",
                "Alexeew",
                User.GenderType.male,
                "aalexeew@gmail.com",
                "89090009900");
        User mike = new User("Mike",
                "Bolck",
                User.GenderType.male,
                "blmike@gmail.com",
                "89009990099");

        List<UserInfo> allUsers= Arrays.asList(Builder.buildUserInfo(alex), Builder.buildUserInfo(mike));

        given(service.findAllUsers()).willReturn(allUsers);

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

        User alex = new User("alex",
                "Alexeew",
                User.GenderType.male,
                "aalexeew@gmail.com",
                "89090009900");

        UserInfo userInfo = Builder.buildUserInfo(alex);

        given(service.findUserById(20)).willReturn(userInfo);

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
        verify(service, times(1)).deleteById(anyInt());
    }

    @Test
    public void createUser()
            throws Exception {

        UserInfo mike = new UserInfo("Mike",
                "Bolck",
                User.GenderType.male,
                "blmike@gmail.com",
                "89009990099");
        given(service.save(refEq(mike))).willReturn(Builder.buildUserByInfo(mike));


        mvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Builder.asJsonString(mike))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(mike));

    }

    @Test
    public void updateUser()
            throws Exception {

        User mike = new User("Mike",
                "Bolck",
                User.GenderType.male,
                "blmike@gmail.com",
                "89009990099");

        UserInfo mikeInfo = Builder.buildUserInfo(mike);

        User mike_new = new User("MikeNew",
                "BlockNew",
                User.GenderType.male,
                "blmikeNew@gmail.com",
                "89009990100");

        UserInfo mikeNewInfo = Builder.buildUserInfo(mike_new);

        mikeNewInfo.setId(21);
        when(service.findUserById(21)).thenReturn(mikeInfo);
        given(service.save(refEq(mikeNewInfo))).willReturn(mike_new);


        mvc.perform(put("/users/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Builder.asJsonString(mikeNewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).save(refEq(mikeNewInfo));
    }
}
