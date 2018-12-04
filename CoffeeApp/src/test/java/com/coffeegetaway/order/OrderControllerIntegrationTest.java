package com.coffeegetaway.order;


import com.coffee.model.helper.JsonMapper;
import com.coffee.model.order.order.OrderInfo;
import com.coffee.model.order.order.OrderMiniInfo;
import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffeegetaway.controller.order.OrderController;
import com.coffeegetaway.service.order.OrderService;
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
        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);

        OrderInfo order1 = new OrderInfo();
        order1.setRecipe(recipe1);
        order1.setCoffeeHouseId(1);
        order1.setUserId(5);


        OrderInfo order2 = new OrderInfo();
        order2.setRecipe(recipe1);
        order2.setCoffeeHouseId(1);
        order2.setUserId(3);


        OrderInfo order3 = new OrderInfo();
        order3.setRecipe(recipe1);
        order3.setCoffeeHouseId(1);
        order3.setUserId(2);

        List<OrderInfo> allOrders= Arrays.asList(order1, order2, order3);

        given(service.allOrders()).willReturn(allOrders);

        mvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(order1.getUserId())))
                .andExpect(jsonPath("$[0].recipe.id", is(order1.getRecipe().getId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].userId", is(order2.getUserId())))
                .andExpect(jsonPath("$[1].recipe.id", is(order2.getRecipe().getId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipe.id", is(order3.getRecipe().getId())))
                .andExpect(jsonPath("$[2].coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void getOrdersByUser()
            throws Exception {
        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);

        OrderInfo order1 = new OrderInfo();
        order1.setRecipe(recipe1);
        order1.setCoffeeHouseId(1);
        order1.setUserId(2);


        OrderInfo order2 = new OrderInfo();
        order2.setRecipe(recipe1);
        order2.setCoffeeHouseId(1);
        order2.setUserId(2);


        OrderInfo order3 = new OrderInfo();
        order3.setRecipe(recipe1);
        order3.setCoffeeHouseId(1);
        order3.setUserId(2);

        List<OrderInfo> allOrders= Arrays.asList(order1, order2, order3);

        given(service.allOrdersbyUser(2)).willReturn(allOrders);

        mvc.perform(get("/orders?user_id=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(order1.getUserId())))
                .andExpect(jsonPath("$[0].recipe.id", is(order1.getRecipe().getId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].userId", is(order2.getUserId())))
                .andExpect(jsonPath("$[1].recipe.id", is(order2.getRecipe().getId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipe.id", is(order3.getRecipe().getId())))
                .andExpect(jsonPath("$[2].coffeeHouseId", is(order3.getCoffeeHouseId())));
    }

    @Test
    public void getOrdersByCoffeeHouse()
            throws Exception {
        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);

        OrderInfo order1 = new OrderInfo();
        order1.setRecipe(recipe1);
        order1.setCoffeeHouseId(1);
        order1.setUserId(5);


        OrderInfo order2 = new OrderInfo();
        order2.setRecipe(recipe1);
        order2.setCoffeeHouseId(1);
        order2.setUserId(3);


        OrderInfo order3 = new OrderInfo();
        order3.setRecipe(recipe1);
        order3.setCoffeeHouseId(1);
        order3.setUserId(2);

        List<OrderInfo> allOrders= Arrays.asList(order1, order2, order3);

        given(service.allOrdersByCoffeeHouse(1)).willReturn(allOrders);

        mvc.perform(get("/orders?coffee_house_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(order1.getUserId())))
                .andExpect(jsonPath("$[0].recipe.id", is(order1.getRecipe().getId())))
                .andExpect(jsonPath("$[0].coffeeHouseId", is(order1.getCoffeeHouseId())))
                .andExpect(jsonPath("$[1].userId", is(order2.getUserId())))
                .andExpect(jsonPath("$[1].recipe.id", is(order2.getRecipe().getId())))
                .andExpect(jsonPath("$[1].coffeeHouseId", is(order2.getCoffeeHouseId())))
                .andExpect(jsonPath("$[2].recipe.id", is(order3.getRecipe().getId())))
                .andExpect(jsonPath("$[2].coffeeHouseId", is(order3.getCoffeeHouseId())));
    }
    @Test
    public void getOrder()
            throws Exception {
        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);

        OrderInfo order1 = new OrderInfo();
        order1.setRecipe(recipe1);
        order1.setCoffeeHouseId(1);
        order1.setUserId(5);

        given(service.findOrderById(20)).willReturn(order1);

        mvc.perform(get("/orders/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(order1.getUserId())))
                .andExpect(jsonPath("$.coffeeHouseId", is(order1.getCoffeeHouseId())));
    }

    @Test
    public void deleteOrder()
            throws Exception {
        mvc.perform(delete("/orders/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteOrder(anyInt());
    }

    @Test
    public void createHouse()
            throws Exception {
        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);
        recipe1.setId(15);

        OrderInfo order1 = new OrderInfo();
        order1.setRecipe(recipe1);
        order1.setCoffeeHouseId(1);
        order1.setUserId(5);

        OrderMiniInfo orderMiniInfo1 = new OrderMiniInfo();
        orderMiniInfo1.setRecipeId(recipe1.getId());
        orderMiniInfo1.setCoffeeHouseId(1);
        orderMiniInfo1.setUserId(5);

        ResponseEntity<OrderInfo> res = new ResponseEntity<OrderInfo>(order1, HttpStatus.CREATED);

        given(service.createOrder(refEq(orderMiniInfo1))).willReturn(res);


        mvc.perform(post("/orders/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(orderMiniInfo1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createOrder(refEq(orderMiniInfo1));

    }

    @Test
    public void updateHouse()
            throws Exception {

        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);
        recipe1.setId(15);

        OrderInfo order1 = new OrderInfo();
        order1.setRecipe(recipe1);
        order1.setCoffeeHouseId(1);
        order1.setUserId(5);

        OrderMiniInfo orderMiniInfo1 = new OrderMiniInfo();
        orderMiniInfo1.setRecipeId(15);
        orderMiniInfo1.setCoffeeHouseId(1);
        orderMiniInfo1.setUserId(5);

        order1.setId(21);

        ResponseEntity<OrderInfo> res = new ResponseEntity<OrderInfo>(order1, HttpStatus.OK);

        given(service.updateOrder(refEq(orderMiniInfo1), refEq(21))).willReturn(res);


        mvc.perform(put("/orders/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(orderMiniInfo1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).updateOrder(refEq(orderMiniInfo1), refEq(21));
    }
}
