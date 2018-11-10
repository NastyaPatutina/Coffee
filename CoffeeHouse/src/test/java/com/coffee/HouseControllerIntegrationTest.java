package com.coffee;

import com.coffee.controller.HouseController;
import com.coffee.entity.House;
import com.coffee.helpers.Builder;
import com.coffee.model.HouseInfo;
import com.coffee.service.house.HouseService;
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

        House house1 = new House("CoffeeHouse 1", "c.Trololo", (float)0.0001, (float)-6.6888);
        House house2 = new House("CoffeeHouse 2", "c.Trololo", (float)68.0001, (float)-12.4567);

        List<HouseInfo> allHouses= Arrays.asList(Builder.buildHouseInfo(house1), Builder.buildHouseInfo(house2));

        given(service.findAllHouses()).willReturn(allHouses);

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


        House house3 = new House("CoffeeHouse 3", "c.Trololo", (float)0.01235, (float)-6.7895);
        HouseInfo houseInfo = Builder.buildHouseInfo(house3);

        given(service.findHouseById(20)).willReturn(houseInfo);

        mvc.perform(get("/houses/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(house3.getName())))
                .andExpect(jsonPath("$.address", is(house3.getAddress())))
                .andExpect(jsonPath("$.latitude").value(house3.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(house3.getLongitude()));
    }

    @Test
    public void deleteHouse()
            throws Exception {
        mvc.perform(delete("/houses/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }

    @Test
    public void createHouse()
            throws Exception {

        House house1 = new House("CoffeeHouse 1", "c.Trololo", (float)0.01235, (float)-6.7895);
        HouseInfo house1Info = Builder.buildHouseInfo(house1);

        given(service.save(refEq(house1Info))).willReturn(house1);


        mvc.perform(post("/houses/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Builder.asJsonString(house1Info))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(house1Info));

    }

    @Test
    public void updateHouse()
            throws Exception {

        House house1 = new House("CoffeeHouse !!!!", "c.Trolololo", (float)0.0135, (float)-6.795);
        HouseInfo house1Info = Builder.buildHouseInfo(house1);
        House house_new = new House("New CoffeeHouse 1", "c.Trololo", (float)0.01235, (float)-6.7895);
        HouseInfo house1NewInfo = Builder.buildHouseInfo(house_new);

        house1NewInfo.setId(21);

        given(service.findHouseById(21)).willReturn(house1Info);
        given(service.save(refEq(house1NewInfo))).willReturn(house_new);


        mvc.perform(put("/houses/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Builder.asJsonString(house1NewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).save(refEq(house1NewInfo));
    }
}
