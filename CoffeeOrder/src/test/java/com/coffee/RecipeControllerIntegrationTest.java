package com.coffee;

import com.coffee.controller.RecipeController;
import com.coffee.entity.Recipe;
import com.coffee.entity.RecipeIngredient;
import com.coffee.helpers.Builder;
import com.coffee.model.helper.JsonMapper;
import com.coffee.model.order.recipe.*;
import com.coffee.service.recipe.RecipeService;
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
@WebMvcTest(RecipeController.class)
public class RecipeControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RecipeService service;

    private static Logger logger = LoggerFactory.getLogger(RecipeControllerIntegrationTest.class);

    @Test
    public void getRecipes()
            throws Exception {

        Recipe recipe1 = new Recipe("Recipe 1", 50);
        Recipe recipe2 = new Recipe("Recipe 2", 300);
        Recipe recipe3 = new Recipe("Recipe 3", 150);

        List<RecipeWithIngredientsInfo> allRecipes= Arrays.asList(Builder.buildRecipeInfoWithIngredients(recipe1),
                Builder.buildRecipeInfoWithIngredients(recipe2),
                Builder.buildRecipeInfoWithIngredients(recipe3));

        given(service.findAllRecipes()).willReturn(allRecipes);

        mvc.perform(get("/recipes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(recipe1.getName())))
                .andExpect(jsonPath("$[0].cost", is(recipe1.getCost())))
                .andExpect(jsonPath("$[1].name", is(recipe2.getName())))
                .andExpect(jsonPath("$[1].cost", is(recipe2.getCost())))
                .andExpect(jsonPath("$[2].name", is(recipe3.getName())))
                .andExpect(jsonPath("$[2].cost", is(recipe3.getCost())));
    }

    @Test
    public void getRecipe()
            throws Exception {
        Recipe recipe3 = new Recipe("Recipe 3", 150);
        RecipeWithIngredientsInfo recipeInfo = Builder.buildRecipeInfoWithIngredients(recipe3);

        given(service.findRecipeById(20)).willReturn(recipeInfo);

        mvc.perform(get("/recipes/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipe3.getName())))
                .andExpect(jsonPath("$.cost", is(recipe3.getCost())));
    }

    @Test
    public void getRecipeIngredients()
            throws Exception {
        Recipe recipe3 = new Recipe("Recipe 3", 200);

        ArrayList<RecipeIngredient>recipeIngredients = new ArrayList<>();
        recipeIngredients.add(new RecipeIngredient(5, recipe3, 20));
        recipeIngredients.add(new RecipeIngredient(2, recipe3, 10));
        recipe3.setRecipeIngredients(recipeIngredients);

        RecipeWithIngredientsInfo recipeInfo = Builder.buildRecipeInfoWithIngredients(recipe3);

        given(service.findRecipeById(20)).willReturn(recipeInfo);

        mvc.perform(get("/recipes/20/ingredients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].count", is(recipeIngredients.get(0).getCount())))
                .andExpect(jsonPath("$[0].productId", is(recipeIngredients.get(0).getProductId())))
                .andExpect(jsonPath("$[1].count", is(recipeIngredients.get(1).getCount())))
                .andExpect(jsonPath("$[1].productId", is(recipeIngredients.get(1).getProductId())));
    }

    @Test
    public void deleteRecipe()
            throws Exception {
        mvc.perform(delete("/recipes/12"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(anyInt());
    }

    @Test
    public void createRecipe()
            throws Exception {
        Recipe recipe1 = new Recipe("Recipe 3", 150);
        RecipeWithIngredientsInfo recipe1Info = Builder.buildRecipeInfoWithIngredients(recipe1);

        given(service.save(refEq(recipe1Info))).willReturn(recipe1);


        mvc.perform(post("/recipes/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipe1Info))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(refEq(recipe1Info));

    }

    @Test
    public void updateRecipe()
            throws Exception {

        Recipe recipe1 = new Recipe("Recipe 3", 150);
        RecipeWithIngredientsInfo recipe1Info = Builder.buildRecipeInfoWithIngredients(recipe1);
        Recipe recipe_new = new Recipe("Recipe 1", 200);
        RecipeInfo recipe1NewInfo = Builder.buildRecipeInfo(recipe_new);

        recipe1NewInfo.setId(21);

        given(service.findRecipeById(21)).willReturn(recipe1Info);
        given(service.save(refEq(recipe1NewInfo))).willReturn(recipe_new);


        mvc.perform(put("/recipes/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipe1NewInfo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).save(refEq(recipe1NewInfo));
    }
}
