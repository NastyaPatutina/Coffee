package com.coffeegetaway.house;

import com.coffee.model.helper.JsonMapper;
import com.coffee.model.house.HouseInfo;
import com.coffee.model.house.ProductInfo;
import com.coffeegetaway.controller.house.ProductController;
import com.coffeegetaway.service.house.ProductService;
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
@WebMvcTest(ProductController.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    private static Logger logger = LoggerFactory.getLogger(ProductControllerIntegrationTest.class);


    @Test
    public void getProducts()
            throws Exception {

        ProductInfo coffee1 = new ProductInfo();
        coffee1.setName("coffee1");
        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");

        List<ProductInfo> allProducts= Arrays.asList(coffee1, coffee2);

        given(service.allProducts()).willReturn(allProducts);

        mvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(coffee1.getName())))
                .andExpect(jsonPath("$[1].name", is(coffee2.getName())));
    }

    @Test
    public void getProduct()
            throws Exception {

        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");

        given(service.findProductById(20)).willReturn(coffee2);

        mvc.perform(get("/products/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(coffee2.getName())));
    }

    @Test
    public void deleteProduct()
            throws Exception {
        mvc.perform(delete("/products/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteProduct(anyInt());
    }

    @Test
    public void createProduct()
            throws Exception {


        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee2");

        ResponseEntity<ProductInfo> res = new ResponseEntity<ProductInfo>(coffee2, HttpStatus.CREATED);

        given(service.createProduct(refEq(coffee2))).willReturn(res);

        mvc.perform(post("/products/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(coffee2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createProduct(refEq(coffee2));

    }

    @Test
    public void updateProduct()
            throws Exception {

        ProductInfo coffee2 = new ProductInfo();
        coffee2.setName("coffee newww");

        coffee2.setId(21);
        ResponseEntity<ProductInfo> res = new ResponseEntity<ProductInfo>(coffee2, HttpStatus.OK);

        given(service.updateProduct(refEq(coffee2), refEq(21))).willReturn(res);


        mvc.perform(put("/products/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(coffee2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).updateProduct(refEq(coffee2), refEq(21));
    }
}
