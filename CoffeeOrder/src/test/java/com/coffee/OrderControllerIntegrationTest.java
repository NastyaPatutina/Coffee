package com.coffee;

import com.coffee.controller.OrderController;
import com.coffee.entity.Recipe;
import com.coffee.entity.Order;
import com.coffee.helpers.Builder;
import com.coffee.model.helper.JsonMapper;
import com.coffee.model.order.order.*;
import com.coffee.service.order.OrderService;
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

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService service;

    private static Logger logger = LoggerFactory.getLogger(OrderControllerIntegrationTest.class);

    @Test
    public void getOrders()
            throws Exception {
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        Order order1 = new Order(5, recipe1, 1);
        Order order2 = new Order(2, recipe1, 1);
        Order order3 = new Order(3, recipe1, 2);

        List<OrderInfo> allOrders= Arrays.asList(Builder.buildOrderInfo(order1),
                Builder.buildOrderInfo(order2),
                Builder.buildOrderInfo(order3));

        given(service.findAllOrders()).willReturn(allOrders);

        mvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].customerId", is(order1.getCustomerId())))
                .andExpect(jsonPath("$[0].recipe.id", is(order1.getRecipeId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].customerId", is(order2.getCustomerId())))
                .andExpect(jsonPath("$[1].recipe.id", is(order2.getRecipeId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipe.id", is(order3.getRecipeId())))
                .andExpect(jsonPath("$[2].coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void getOrdersForCustomer()
            throws Exception {
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        Order order1 = new Order(2, recipe1, 1);
        Order order2 = new Order(2, recipe1, 1);
        Order order3 = new Order(2, recipe1, 2);

        List<OrderInfo> allOrders= Arrays.asList(Builder.buildOrderInfo(order1),
                Builder.buildOrderInfo(order2),
                Builder.buildOrderInfo(order3));

        given(service.findOrderByCustomerId(2)).willReturn(allOrders);

        mvc.perform(get("/orders?customer_id=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].customerId", is(order1.getCustomerId())))
                .andExpect(jsonPath("$[0].recipe.id", is(order1.getRecipe().getId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].customerId", is(order2.getCustomerId())))
                .andExpect(jsonPath("$[1].recipe.id", is(order2.getRecipe().getId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipe.id", is(order3.getRecipe().getId())))
                .andExpect(jsonPath("$[2].coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void getOrdersForCoffeeHouse()
            throws Exception {
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        Order order1 = new Order(5, recipe1, 1);
        Order order2 = new Order(2, recipe1, 1);
        Order order3 = new Order(3, recipe1, 1);

        List<OrderInfo> allOrders= Arrays.asList(Builder.buildOrderInfo(order1),
                Builder.buildOrderInfo(order2),
                Builder.buildOrderInfo(order3));

        given(service.findOrderByCoffeeHouseId(1)).willReturn(allOrders);

        mvc.perform(get("/orders?coffee_house_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].customerId", is(order1.getCustomerId())))
                .andExpect(jsonPath("$[0].recipe.id", is(order1.getRecipeId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].customerId", is(order2.getCustomerId())))
                .andExpect(jsonPath("$[1].recipe.id", is(order2.getRecipeId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipe.id", is(order3.getRecipeId())))
                .andExpect(jsonPath("$[2].coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void getOrder()
            throws Exception {
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        Order order3 = new Order(1, recipe1, 10);
        OrderInfo orderInfo = Builder.buildOrderInfo(order3);

        given(service.findOrderById(20)).willReturn(orderInfo);

        mvc.perform(get("/orders/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(order3.getCustomerId())))
                .andExpect(jsonPath("$.coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void deleteOrder()
            throws Exception {
        mvc.perform(delete("/orders/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }

    @Test
    public void createHouse()
            throws Exception {

        Recipe recipe1 = new Recipe("Recipe 1", 50);

        Order order3 = new Order(1, recipe1, 10);
        OrderMiniInfo orderInfo = Builder.buildOrderMiniInfo(order3);

        given(service.save(refEq(orderInfo))).willReturn(order3);


        mvc.perform(post("/orders/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(orderInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(orderInfo));

    }

    @Test
    public void updateHouse()
            throws Exception {
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        Order order3 = new Order(1, recipe1, 10);
        OrderInfo orderInfo = Builder.buildOrderInfo(order3);
        Order order_new = new Order(1, recipe1, 10);
        OrderMiniInfo orderNewInfo = Builder.buildOrderMiniInfo(order3);

        orderNewInfo.setId(21);

        given(service.findOrderById(21)).willReturn(orderInfo);
        given(service.save(refEq(orderNewInfo))).willReturn(order_new);


        mvc.perform(put("/orders/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(orderNewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).save(refEq(orderNewInfo));
    }
}
