package com.coffee;

import com.coffee.controller.StorageController;
import com.coffee.entity.House;
import com.coffee.entity.Product;
import com.coffee.entity.Storage;
import com.coffee.helpers.Builder;
import com.coffee.model.helper.JsonMapper;
import com.coffee.model.house.storage.*;
import com.coffee.service.storage.StorageService;
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

        House house1 = new House("CoffeeHouse 1", "c.Trololo", (float)0.0001, (float)-6.6888);
        House house2 = new House("CoffeeHouse 2", "c.Trolo", (float)0.12345, (float)-86.55);
        Product coffee1 = new Product("coffee1");
        Product coffee2 = new Product("coffee2");

        Storage storage1 = new Storage(house1, coffee2, 300);
        Storage storage2 = new Storage(house1, coffee1, 200);
        Storage storage3 = new Storage(house2, coffee2, 50);

        List<StorageInfo> allStorages= Arrays.asList(Builder.buildStorageInfo(storage1),
                Builder.buildStorageInfo(storage2),
                Builder.buildStorageInfo(storage3));

        given(service.findAllStorage()).willReturn(allStorages);

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


        House house1 = new House("CoffeeHouse 1", "c.Trololo", (float)0.0001, (float)-6.6888);
        Product coffee1 = new Product("coffee1");
        Product coffee2 = new Product("coffee2");


        Storage storage1 = new Storage(house1, coffee2, 300);
        Storage storage2 = new Storage(house1, coffee1, 200);


        List<StorageInfo> allStorages= Arrays.asList(Builder.buildStorageInfo(storage1),
                Builder.buildStorageInfo(storage2));

        given(service.findStorageForHouse(5)).willReturn(allStorages);

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
        House house = new House("CoffeeHouse 1", "c.Trololo", (float)0.0001, (float)-6.6888);
        Product coffee = new Product("coffee2");

        Storage storage = new Storage();
        storage.setHouse(house);
        storage.setProduct(coffee);
        storage.setCount(300);
        StorageInfo storageInfo = Builder.buildStorageInfo(storage);

        given(service.findStorageById(20)).willReturn(storageInfo);

        mvc.perform(get("/storage/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(storage.getCount())))
                .andExpect(jsonPath("$.house.name", is(storage.getHouse().getName())))
                .andExpect(jsonPath("$.house.address", is(storage.getHouse().getAddress())))
                .andExpect(jsonPath("$.house.longitude").value(storage.getHouse().getLongitude()))
                .andExpect(jsonPath("$.house.latitude").value(storage.getHouse().getLatitude()))
                .andExpect(jsonPath("$.product.name", is(storage.getProduct().getName())));
    }

    @Test
    public void deleteStorage()
            throws Exception {
        mvc.perform(delete("/storage/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }


    @Test
    public void createStorage()
            throws Exception {

        House house = new House("CoffeeHouse 1", "c.Trololo", (float)0.0001, (float)-6.6888);
        Product coffee = new Product("coffee2");

        Storage storage = new Storage();
        storage.setHouse(house);
        storage.setProduct(coffee);
        storage.setCount(300);
        StorageMiniInfo storageInfo = Builder.buildStorageMiniInfo(storage);

        given(service.save(refEq(storageInfo))).willReturn(storage);


        mvc.perform(post("/storage/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(storageInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(storageInfo));

    }

    @Test
    public void updateStorage()
            throws Exception {


        House house = new House("CoffeeHouse 1", "c.Trololo", (float)0.0001, (float)-6.6888);
        Product coffee = new Product("coffee2");

        Storage storage = new Storage();
        storage.setHouse(house);
        storage.setProduct(coffee);
        storage.setCount(300);
        StorageInfo storageInfo = Builder.buildStorageInfo(storage);

        Storage storageNew = new Storage();
        storageNew.setHouse(house);
        storageNew.setProduct(coffee);
        storageNew.setCount(200);

        StorageMiniInfo storageNewInfo = Builder.buildStorageMiniInfo(storageNew);

        storageNewInfo.setId(21);

        given(service.findStorageById(21)).willReturn(storageInfo);
        given(service.save(refEq(storageNewInfo))).willReturn(storageNew);


        mvc.perform(put("/storage/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(storageNewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).save(refEq(storageNewInfo));
    }
}
