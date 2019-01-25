package com.coffeegetaway.house;


import com.coffee.model.helper.JsonMapper;
import com.coffee.model.house.HouseInfo;
import com.coffee.model.order.order.OrderInfo;
import com.coffeegetaway.controller.house.HouseController;
import com.coffeegetaway.service.house.HouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HouseController.class)
public class HouseControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HouseService service;

    private static Logger logger = LoggerFactory.getLogger(HouseControllerIntegrationTest.class);

    @Test
    public void getHouses()
            throws Exception {

        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse 1");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.0001);
        house1.setLongitude((float)-6.6888);

        HouseInfo house2 = new HouseInfo();
        house2.setName("CoffeeHouse 2");
        house2.setAddress("c.Trololo");
        house2.setLatitude((float)68.0001);
        house2.setLongitude((float)-12.4567);

        List<HouseInfo> allHouses= Arrays.asList(house1,house2);

        given(service.allHouses()).willReturn(allHouses);

        mvc.perform(get("/houses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(house1.getName())))
                .andExpect(jsonPath("$[0].address", is(house1.getAddress())))
                .andExpect(jsonPath("$[0].latitude").value(house1.getLatitude()))
                .andExpect(jsonPath("$[0].longitude").value(house1.getLongitude()))
                .andExpect(jsonPath("$[1].name", is(house2.getName())))
                .andExpect(jsonPath("$[1].address", is(house2.getAddress())))
                .andExpect(jsonPath("$[1].latitude").value(house2.getLatitude()))
                .andExpect(jsonPath("$[1].longitude").value(house2.getLongitude()));
    }

    @Test
    public void getHouse()
            throws Exception {

        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse 3");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.01235);
        house1.setLongitude((float)-6.7895);

        given(service.findHouseById(20)).willReturn(house1);

        mvc.perform(get("/houses/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(house1.getName())))
                .andExpect(jsonPath("$.address", is(house1.getAddress())))
                .andExpect(jsonPath("$.latitude").value(house1.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(house1.getLongitude()));
    }

    @Test
    public void deleteHouse()
            throws Exception {
        mvc.perform(delete("/houses/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteHouse(anyInt());
    }

    @Test
    public void createHouse()
            throws Exception {

        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse 3");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.01235);
        house1.setLongitude((float)-6.7895);

        ResponseEntity<HouseInfo> res = new ResponseEntity<HouseInfo>(house1, HttpStatus.CREATED);

        given(service.createHouse(refEq(house1))).willReturn(res);


        mvc.perform(post("/houses/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(house1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createHouse(refEq(house1));

    }

    @Test
    public void updateHouse()
            throws Exception {

        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse New");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.01235);
        house1.setLongitude((float)-6.7895);

        house1.setId(21);

        ResponseEntity<HouseInfo> res = new ResponseEntity<HouseInfo>(house1, HttpStatus.OK);

        given(service.updateHouse(refEq(house1), refEq(21))).willReturn(res);


        mvc.perform(put("/houses/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(house1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).updateHouse(refEq(house1), refEq(21));
    }
}

