package com.coffeegetaway.house;


import com.coffee.model.helper.JsonMapper;
import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.ProductInfo;
import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.house.storage.StorageMiniInfo;
import com.coffee.model.order.order.OrderInfo;
import com.coffeegetaway.controller.house.StorageController;
import com.coffeegetaway.service.house.StorageService;
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
@WebMvcTest(StorageController.class)
public class StorageControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService service;

    private static Logger logger = LoggerFactory.getLogger(StorageControllerIntegrationTest.class);

    @Test
    public void getAllStorage()
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

        ProductInfo coffee1 = new ProductInfo();
        coffee1.setName("coffee1");
        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");

        StorageInfo storage1 = new StorageInfo();
        storage1.setHouse(house1);
        storage1.setProduct(coffee2);
        storage1.setCount(300);

        StorageInfo storage2 = new StorageInfo();
        storage2.setHouse(house1);
        storage2.setProduct(coffee1);
        storage2.setCount(200);

        StorageInfo storage3 = new StorageInfo();
        storage3.setHouse(house2);
        storage3.setProduct(coffee2);
        storage3.setCount(50);

        List<StorageInfo> allStorages= Arrays.asList(storage1, storage2, storage3);

        given(service.allStorage()).willReturn(allStorages);

        mvc.perform(get("/storage")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].count", is(storage1.getCount())))
                .andExpect(jsonPath("$[0].house.name", is(storage1.getHouse().getName())))
                .andExpect(jsonPath("$[0].house.address", is(storage1.getHouse().getAddress())))
                .andExpect(jsonPath("$[0].house.longitude").value(storage1.getHouse().getLongitude()))
                .andExpect(jsonPath("$[0].house.latitude").value(storage1.getHouse().getLatitude()))
                .andExpect(jsonPath("$[0].product.name", is(storage1.getProduct().getName())))
                .andExpect(jsonPath("$[1].count", is(storage2.getCount())))
                .andExpect(jsonPath("$[1].house.name", is(storage2.getHouse().getName())))
                .andExpect(jsonPath("$[1].house.address", is(storage2.getHouse().getAddress())))
                .andExpect(jsonPath("$[1].house.longitude").value(storage2.getHouse().getLongitude()))
                .andExpect(jsonPath("$[1].house.latitude").value(storage2.getHouse().getLatitude()))
                .andExpect(jsonPath("$[1].product.name", is(storage2.getProduct().getName())))
                .andExpect(jsonPath("$[2].count", is(storage3.getCount())))
                .andExpect(jsonPath("$[2].house.name", is(storage3.getHouse().getName())))
                .andExpect(jsonPath("$[2].house.address", is(storage3.getHouse().getAddress())))
                .andExpect(jsonPath("$[2].house.longitude").value(storage3.getHouse().getLongitude()))
                .andExpect(jsonPath("$[2].house.latitude").value(storage3.getHouse().getLatitude()))
                .andExpect(jsonPath("$[2].product.name", is(storage3.getProduct().getName())));
    }

    @Test
    public void getStorageByHouse()
            throws Exception {

        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse 1");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.0001);
        house1.setLongitude((float)-6.6888);


        ProductInfo coffee1 = new ProductInfo();
        coffee1.setName("coffee1");
        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");

        StorageInfo storage1 = new StorageInfo();
        storage1.setHouse(house1);
        storage1.setProduct(coffee2);
        storage1.setCount(300);

        StorageInfo storage2 = new StorageInfo();
        storage2.setHouse(house1);
        storage2.setProduct(coffee1);
        storage2.setCount(200);

        List<StorageInfo> allStorages= Arrays.asList(storage1, storage2);

        given(service.allStorageForHouse(5)).willReturn(allStorages);

        mvc.perform(get("/storage?house_id=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].count", is(storage1.getCount())))
                .andExpect(jsonPath("$[0].house.name", is(storage1.getHouse().getName())))
                .andExpect(jsonPath("$[0].house.address", is(storage1.getHouse().getAddress())))
                .andExpect(jsonPath("$[0].house.longitude").value(storage1.getHouse().getLongitude()))
                .andExpect(jsonPath("$[0].house.latitude").value(storage1.getHouse().getLatitude()))
                .andExpect(jsonPath("$[0].product.name", is(storage1.getProduct().getName())))
                .andExpect(jsonPath("$[1].count", is(storage2.getCount())))
                .andExpect(jsonPath("$[1].house.name", is(storage2.getHouse().getName())))
                .andExpect(jsonPath("$[1].house.address", is(storage2.getHouse().getAddress())))
                .andExpect(jsonPath("$[1].house.longitude").value(storage2.getHouse().getLongitude()))
                .andExpect(jsonPath("$[1].house.latitude").value(storage2.getHouse().getLatitude()))
                .andExpect(jsonPath("$[1].product.name", is(storage2.getProduct().getName())));
    }

    @Test
    public void getStorage()
            throws Exception {

        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse 1");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.0001);
        house1.setLongitude((float)-6.6888);


        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");

        StorageInfo storage1 = new StorageInfo();
        storage1.setHouse(house1);
        storage1.setProduct(coffee2);
        storage1.setCount(300);

        given(service.findStorageById(20)).willReturn(storage1);

        mvc.perform(get("/storage/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(storage1.getCount())))
                .andExpect(jsonPath("$.house.name", is(storage1.getHouse().getName())))
                .andExpect(jsonPath("$.house.address", is(storage1.getHouse().getAddress())))
                .andExpect(jsonPath("$.house.longitude").value(storage1.getHouse().getLongitude()))
                .andExpect(jsonPath("$.house.latitude").value(storage1.getHouse().getLatitude()))
                .andExpect(jsonPath("$.product.name", is(storage1.getProduct().getName())));
    }

    @Test
    public void deleteStorage()
            throws Exception {
        mvc.perform(delete("/storage/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteStorage(anyInt());
    }


    @Test
    public void createStorage()
            throws Exception {


        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse 1");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.0001);
        house1.setLongitude((float)-6.6888);
        house1.setId(15);

        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");
        coffee2.setId(51);

        StorageInfo storage1 = new StorageInfo();
        storage1.setHouse(house1);
        storage1.setProduct(coffee2);
        storage1.setCount(300);

        StorageMiniInfo storageMini = new StorageMiniInfo();
        storageMini.setHouseId(house1.getId());
        storageMini.setProductId(coffee2.getId());
        storageMini.setCount(300);

        ResponseEntity<StorageInfo> res = new ResponseEntity<StorageInfo>(storage1, HttpStatus.CREATED);

        given(service.createStorage(refEq(storageMini))).willReturn(res);


        mvc.perform(post("/storage/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(storageMini))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createStorage(refEq(storageMini));

    }

    @Test
    public void updateStorage()
            throws Exception {

        HouseInfo house1 = new HouseInfo();
        house1.setName("CoffeeHouse 1");
        house1.setAddress("c.Trololo");
        house1.setLatitude((float)0.0001);
        house1.setLongitude((float)-6.6888);
        house1.setId(15);

        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");
        coffee2.setId(51);

        StorageInfo storage1 = new StorageInfo();
        storage1.setHouse(house1);
        storage1.setProduct(coffee2);
        storage1.setCount(300);

        StorageMiniInfo storageMini = new StorageMiniInfo();
        storageMini.setHouseId(house1.getId());
        storageMini.setProductId(coffee2.getId());
        storageMini.setCount(300);

        storage1.setId(21);
        ResponseEntity<StorageInfo> res = new ResponseEntity<StorageInfo>(storage1, HttpStatus.OK);

        given(service.updateStorage(refEq(storageMini), refEq(21))).willReturn(res);


        mvc.perform(put("/storage/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(storageMini))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).updateStorage(refEq(storageMini), refEq(21));
    }
}
