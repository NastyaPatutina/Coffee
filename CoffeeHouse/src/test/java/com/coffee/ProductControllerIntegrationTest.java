package com.coffee;

import com.coffee.controller.ProductController;
import com.coffee.entity.Product;
import com.coffee.helpers.Builder;
import com.coffee.model.ProductInfo;
import com.coffee.service.product.ProductService;
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

        Product alex = new Product("coffe1");
        Product mike = new Product("coffe2");

        List<ProductInfo> allProducts= Arrays.asList(Builder.buildProductInfo(alex), Builder.buildProductInfo(mike));

        given(service.findAllProducts()).willReturn(allProducts);

        mvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(alex.getName())))
                .andExpect(jsonPath("$[1].name", is(mike.getName())));
    }

    @Test
    public void getProduct()
            throws Exception {

        Product alex = new Product("coffee3");

        ProductInfo productInfo = Builder.buildProductInfo(alex);

        given(service.findProductById(20)).willReturn(productInfo);

        mvc.perform(get("/products/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(alex.getName())));
    }

    @Test
    public void deleteProduct()
            throws Exception {
        mvc.perform(delete("/products/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }
}
