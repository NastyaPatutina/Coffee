package com.coffeegetaway.order;


import com.coffee.model.helper.JsonMapper;
import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeMiniIngredientInfo;
import com.coffeegetaway.controller.order.RecipeIngredientController;
import com.coffeegetaway.service.order.RecipeIngredientService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;

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
@WebMvcTest(RecipeIngredientController.class)
public class RecipeIngredientControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RecipeIngredientService service;

    private static Logger logger = LoggerFactory.getLogger(RecipeIngredientControllerIntegrationTest.class);

    @Test
    public void getRecipeIngredients()
            throws Exception {

        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);

        RecipeIngredientInfo recipeIngredient1 = new RecipeIngredientInfo();
        recipeIngredient1.setProductId(5);
        recipeIngredient1.setRecipe(recipe1);
        recipeIngredient1.setCount(50);

        RecipeIngredientInfo recipeIngredient2 = new RecipeIngredientInfo();
        recipeIngredient2.setProductId(2);
        recipeIngredient2.setRecipe(recipe1);
        recipeIngredient2.setCount(100);
        RecipeIngredientInfo recipeIngredient3 = new RecipeIngredientInfo();
        recipeIngredient3.setProductId(3);
        recipeIngredient3.setRecipe(recipe1);
        recipeIngredient3.setCount(10);

        List<RecipeIngredientInfo> allRecipeIngredients= Arrays.asList(recipeIngredient1,
                recipeIngredient2,
                recipeIngredient3);

        given(service.allRecipeIngredients()).willReturn(allRecipeIngredients);

        mvc.perform(get("/recipe_ingredients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productId", is(recipeIngredient1.getProductId())))
                .andExpect(jsonPath("$[0].count", is(recipeIngredient1.getCount())))
                .andExpect(jsonPath("$[1].productId", is(recipeIngredient2.getProductId())))
                .andExpect(jsonPath("$[1].count", is(recipeIngredient2.getCount())))
                .andExpect(jsonPath("$[2].productId", is(recipeIngredient3.getProductId())))
                .andExpect(jsonPath("$[2].count", is(recipeIngredient3.getCount())));
    }

    @Test
    public void getRecipeIngredient()
            throws Exception {
        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);

        RecipeIngredientInfo recipeIngredient1 = new RecipeIngredientInfo();
        recipeIngredient1.setProductId(5);
        recipeIngredient1.setRecipe(recipe1);
        recipeIngredient1.setCount(50);

        given(service.findRecipeIngredientById(20)).willReturn(recipeIngredient1);

        mvc.perform(get("/recipe_ingredients/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(recipeIngredient1.getProductId())))
                .andExpect(jsonPath("$.count", is(recipeIngredient1.getCount())));
    }

    @Test
    public void deleteRecipeIngredient()
            throws Exception {
        mvc.perform(delete("/recipe_ingredients/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteRecipeIngredient(anyInt());
    }

    @Test
    public void createRecipeIngredient()
            throws Exception {

        RecipeInfo recipe1 = new RecipeInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);
        recipe1.setId(15);

        RecipeMiniIngredientInfo recipeIngredientMini1 = new RecipeMiniIngredientInfo();
        recipeIngredientMini1.setProductId(5);
        recipeIngredientMini1.setRecipeId(recipe1.getId());
        recipeIngredientMini1.setCount(50);

        RecipeIngredientInfo recipeIngredient1 = new RecipeIngredientInfo();
        recipeIngredient1.setProductId(5);
        recipeIngredient1.setRecipe(recipe1);
        recipeIngredient1.setCount(50);

        ResponseEntity<RecipeIngredientInfo> res = new ResponseEntity<RecipeIngredientInfo>(recipeIngredient1, HttpStatus.CREATED);

        given(service.createRecipeIngredient(refEq(recipeIngredientMini1))).willReturn(res);


        mvc.perform(post("/recipe_ingredients/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipeIngredientMini1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createRecipeIngredient(refEq(recipeIngredientMini1));

    }

    @Test
    public void updateRecipeIngredient()
            throws Exception {

        RecipeInfo recipe2 = new RecipeInfo();
        recipe2.setName("Recipe 2");
        recipe2.setCost(40);
        recipe2.setId(11);

        RecipeMiniIngredientInfo recipeIngredientMini2 = new RecipeMiniIngredientInfo();
        recipeIngredientMini2.setProductId(6);
        recipeIngredientMini2.setRecipeId(recipe2.getId());
        recipeIngredientMini2.setCount(40);

        RecipeIngredientInfo recipeIngredient2 = new RecipeIngredientInfo();
        recipeIngredient2.setProductId(6);
        recipeIngredient2.setRecipe(recipe2);
        recipeIngredient2.setCount(40);

        recipeIngredientMini2.setId(21);

        ResponseEntity<RecipeIngredientInfo> res = new ResponseEntity<RecipeIngredientInfo>(recipeIngredient2, HttpStatus.CREATED);

        given(service.updateRecipeIngredient(refEq(recipeIngredientMini2), refEq(21))).willReturn(res);


        mvc.perform(put("/recipe_ingredients/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipeIngredientMini2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).updateRecipeIngredient(refEq(recipeIngredientMini2), refEq(21));
    }

}
