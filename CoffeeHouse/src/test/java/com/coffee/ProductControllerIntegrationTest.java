package com.coffee;

import com.coffee.controller.ProductController;
import com.coffee.entity.Product;
import com.coffee.helpers.Builder;
import com.coffee.model.helper.JsonMapper;
import com.coffee.model.house.ProductInfo;
import com.coffee.service.product.ProductService;
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

        Product coffee1 = new Product("coffee1");
        Product coffee2 = new Product("coffee2");

        List<ProductInfo> allProducts= Arrays.asList(Builder.buildProductInfo(coffee1), Builder.buildProductInfo(coffee2));

        given(service.findAllProducts()).willReturn(allProducts);

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

        Product coffee3 = new Product("coffee3");

        ProductInfo productInfo = Builder.buildProductInfo(coffee3);

        given(service.findProductById(20)).willReturn(productInfo);

        mvc.perform(get("/products/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(coffee3.getName())));
    }

    @Test
    public void deleteProduct()
            throws Exception {
        mvc.perform(delete("/products/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }

    @Test
    public void createProduct()
            throws Exception {

        Product product1 = new Product("Product1");
        ProductInfo product1Info = Builder.buildProductInfo(product1);

        given(service.save(refEq(product1Info))).willReturn(product1);


        mvc.perform(post("/products/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(product1Info))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(product1Info));

    }

    @Test
    public void updateProduct()
            throws Exception {

        Product product1 = new Product("Product1");
        ProductInfo product1Info = Builder.buildProductInfo(product1);
        Product product_new = new Product("ProductNew");
        ProductInfo product1NewInfo = Builder.buildProductInfo(product_new);

        product1NewInfo.setId(21);

        given(service.findProductById(21)).willReturn(product1Info);
        given(service.save(refEq(product1NewInfo))).willReturn(product_new);


        mvc.perform(put("/products/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(product1NewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).save(refEq(product1NewInfo));
    }
}
