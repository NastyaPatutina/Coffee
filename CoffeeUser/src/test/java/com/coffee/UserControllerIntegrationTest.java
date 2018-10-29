package com.coffee;

import com.coffee.controller.UserController;
import com.coffee.entity.User;
import com.coffee.model.UserInfo;
import com.coffee.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void givenUsers_whenGetUsers_thenReturnJson()
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

        List<UserInfo> allUsers= Arrays.asList(buildUserInfo(alex), buildUserInfo(mike));

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

    @Nonnull
    private UserInfo buildUserInfo(User user) {
        UserInfo info = new UserInfo();
        info.setId(user.getId());
        info.setFirstName(user.getFirstName());
        info.setLastName(user.getLastName());
        info.setGender(user.getGender());
        info.setEmail(user.getEmail());
        info.setPhone(user.getPhone());
        return info;
    }
}
