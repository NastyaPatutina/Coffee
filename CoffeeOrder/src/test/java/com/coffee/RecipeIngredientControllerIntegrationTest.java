package com.coffee;

import com.coffee.controller.RecipeIngredientController;
import com.coffee.entity.Recipe;
import com.coffee.entity.RecipeIngredient;
import com.coffee.helpers.Builder;
import com.coffee.model.helper.JsonMapper;
import com.coffee.model.order.recipeIngredient.*;
import com.coffee.service.recipeIngredient.RecipeIngredientService;
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
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        RecipeIngredient recipeIngredient1 = new RecipeIngredient(5, recipe1, 50);
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(2, recipe1, 100);
        RecipeIngredient recipeIngredient3 = new RecipeIngredient(3, recipe1, 10);

        List<RecipeIngredientInfo> allRecipeIngredients= Arrays.asList(Builder.buildRecipeIngredientInfo(recipeIngredient1),
                Builder.buildRecipeIngredientInfo(recipeIngredient2),
                Builder.buildRecipeIngredientInfo(recipeIngredient3));

        given(service.findAllRecipeIngredients()).willReturn(allRecipeIngredients);

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
        Recipe recipe1 = new Recipe("Recipe 1", 50);

        RecipeIngredient recipeIngredient3 = new RecipeIngredient(1, recipe1, 10);
        RecipeIngredientInfo recipeIngredientInfo = Builder.buildRecipeIngredientInfo(recipeIngredient3);

        given(service.findRecipeIngredientById(20)).willReturn(recipeIngredientInfo);

        mvc.perform(get("/recipe_ingredients/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(recipeIngredient3.getProductId())))
                .andExpect(jsonPath("$.count", is(recipeIngredient3.getCount())));
    }

    @Test
    public void deleteRecipeIngredient()
            throws Exception {
        mvc.perform(delete("/recipe_ingredients/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }

    @Test
    public void createRecipeIngredient()
            throws Exception {

        Recipe recipe1 = new Recipe("Recipe 1", 50);

        RecipeIngredient recipeIngredient3 = new RecipeIngredient(1, recipe1, 10);
        RecipeMiniIngredientInfo recipeIngredientInfo = Builder.buildRecipeIngredientMiniInfo(recipeIngredient3);

        given(service.save(refEq(recipeIngredientInfo))).willReturn(recipeIngredient3);


        mvc.perform(post("/recipe_ingredients/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipeIngredientInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(recipeIngredientInfo));

    }

    @Test
    public void updateRecipeIngredient()
            throws Exception {

        Recipe recipe1 = new Recipe("Recipe 1", 50);

        RecipeIngredient recipeIngredient3 = new RecipeIngredient(1, recipe1, 10);
        RecipeIngredientInfo recipeIngredientInfo = Builder.buildRecipeIngredientInfo(recipeIngredient3);

        Recipe recipe2 = new Recipe("Recipe 2", 50);

        RecipeIngredient recipeIngredientNew3 = new RecipeIngredient(2, recipe2, 12);
        RecipeMiniIngredientInfo recipeIngredientNewInfo = Builder.buildRecipeIngredientMiniInfo(recipeIngredient3);

        recipeIngredientNewInfo.setId(21);

        given(service.findRecipeIngredientById(21)).willReturn(recipeIngredientInfo);
        given(service.save(refEq(recipeIngredientNewInfo))).willReturn(recipeIngredientNew3);


        mvc.perform(put("/recipe_ingredients/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipeIngredientNewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).save(refEq(recipeIngredientNewInfo));
    }

}
