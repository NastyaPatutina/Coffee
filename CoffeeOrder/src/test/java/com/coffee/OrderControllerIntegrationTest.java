package com.coffee;

import com.coffee.controller.OrderController;
import com.coffee.entity.Recipe;
import com.coffee.entity.Order;
import com.coffee.helpers.Builder;
import com.coffee.model.OrderInfo;
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
                .andExpect(jsonPath("$[0].userId", is(order1.getUserId())))
                .andExpect(jsonPath("$[0].recipeId", is(order1.getRecipeId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].userId", is(order2.getUserId())))
                .andExpect(jsonPath("$[1].recipeId", is(order2.getRecipeId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipeId", is(order3.getRecipeId())))
                .andExpect(jsonPath("$[2].coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void getOrdersForUser()
            throws Exception {
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        Order order1 = new Order(2, recipe1, 1);
        Order order2 = new Order(2, recipe1, 1);
        Order order3 = new Order(2, recipe1, 2);

        List<OrderInfo> allOrders= Arrays.asList(Builder.buildOrderInfo(order1),
                Builder.buildOrderInfo(order2),
                Builder.buildOrderInfo(order3));

        given(service.findOrderByUserId(2)).willReturn(allOrders);

        mvc.perform(get("/orders?user_id=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(order1.getUserId())))
                .andExpect(jsonPath("$[0].recipeId", is(order1.getRecipeId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].userId", is(order2.getUserId())))
                .andExpect(jsonPath("$[1].recipeId", is(order2.getRecipeId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipeId", is(order3.getRecipeId())))
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
                .andExpect(jsonPath("$[0].userId", is(order1.getUserId())))
                .andExpect(jsonPath("$[0].recipeId", is(order1.getRecipeId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].userId", is(order2.getUserId())))
                .andExpect(jsonPath("$[1].recipeId", is(order2.getRecipeId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipeId", is(order3.getRecipeId())))
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
                .andExpect(jsonPath("$.userId", is(order3.getUserId())))
                .andExpect(jsonPath("$.coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void deleteOrder()
            throws Exception {
        mvc.perform(delete("/orders/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }
}
