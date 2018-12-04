package com.coffeegetaway.order;

import com.coffee.model.helper.JsonMapper;
import com.coffee.model.house.ProductInfo;
import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipe.RecipeWithProducts;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeIngredientWithProductInfo;
import com.coffeegetaway.controller.order.RecipeController;
import com.coffeegetaway.service.order.RecipeService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        RecipeWithIngredientsInfo recipe1 = new RecipeWithIngredientsInfo();
        recipe1.setName("Recipe 1");
        recipe1.setCost(50);
        RecipeWithIngredientsInfo  recipe2 = new RecipeWithIngredientsInfo();
        recipe2.setName("Recipe 2");
        recipe2.setCost(300);
        RecipeWithIngredientsInfo recipe3 = new RecipeWithIngredientsInfo();
        recipe3.setName("Recipe 3");
        recipe3.setCost(150);

        List<RecipeWithIngredientsInfo> allRecipes= Arrays.asList(recipe1, recipe2, recipe3);

        given(service.allRecipes()).willReturn(allRecipes);

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

        RecipeWithIngredientsInfo recipe3 = new RecipeWithIngredientsInfo();
        recipe3.setName("Recipe 3");
        recipe3.setCost(150);

        given(service.findRecipeById(20)).willReturn(recipe3);

        mvc.perform(get("/recipes/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipe3.getName())))
                .andExpect(jsonPath("$.cost", is(recipe3.getCost())));
    }

    @Test
    public void getRecipeIngredients()
            throws Exception {

        RecipeWithIngredientsInfo recipe3 = new RecipeWithIngredientsInfo();
        recipe3.setName("Recipe 3");
        recipe3.setCost(150);

        ArrayList<OnlyIngredientInfo> recipeIngredients = new ArrayList<>();
        OnlyIngredientInfo oiInfo1 = new OnlyIngredientInfo();
        oiInfo1.setProductId(5);
        oiInfo1.setCount(50);
        recipeIngredients.add(oiInfo1);
        OnlyIngredientInfo oiInfo2 = new OnlyIngredientInfo();
        oiInfo1.setProductId(2);
        oiInfo1.setCount(10);
        recipeIngredients.add(oiInfo2);

        recipe3.setRecipeIngredients(recipeIngredients);

        given(service.findRecipeById(20)).willReturn(recipe3);

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
        verify(service, times(1)).deleteRecipe(anyInt());
    }

    @Test
    public void createRecipe()
            throws Exception {
        RecipeWithProducts recipe3 = new RecipeWithProducts();
        recipe3.setName("Recipe 3");
        recipe3.setCost(150);

        ArrayList<RecipeIngredientWithProductInfo> recipeIngredients = new ArrayList<>();
        RecipeIngredientWithProductInfo oiInfo1 = new RecipeIngredientWithProductInfo();
            ProductInfo prod = new ProductInfo();
            prod.setName("Ololo");
        oiInfo1.setProduct(prod);
        oiInfo1.setCount(50);
        recipeIngredients.add(oiInfo1);

        RecipeIngredientWithProductInfo oiInfo2 = new RecipeIngredientWithProductInfo();
            ProductInfo prod2 = new ProductInfo();
            prod2.setName("Ololo2");
        oiInfo2.setProduct(prod2);
        oiInfo2.setCount(10);
        recipeIngredients.add(oiInfo2);

        RecipeWithProducts recipe3Created = new RecipeWithProducts();
        recipe3Created.setName("Recipe 3");
        recipe3Created.setCost(150);

        ArrayList<RecipeIngredientWithProductInfo> recipeIngredientsreated = new ArrayList<>();
        RecipeIngredientWithProductInfo oiInfo1reated = new RecipeIngredientWithProductInfo();
        ProductInfo prodreated  = new ProductInfo();
        prodreated.setName("Ololo");
        prodreated.setId(15);
        oiInfo1reated.setProduct(prod);
        oiInfo1reated.setCount(50);
        oiInfo1reated.setId(16);
        recipeIngredientsreated.add(oiInfo1);

        RecipeIngredientWithProductInfo oiInfo2reated = new RecipeIngredientWithProductInfo();
        ProductInfo prod2reated = new ProductInfo();
        prod2reated.setName("Ololo2");
        prod2reated.setId(17);
        oiInfo2reated.setProduct(prod2);
        oiInfo2reated.setCount(10);
        oiInfo2reated.setId(18);
        recipeIngredientsreated.add(oiInfo2);

        recipe3Created.setRecipeIngredients(recipeIngredients);
        recipe3Created.setId(19);

        ResponseEntity<RecipeWithProducts> res = new ResponseEntity<RecipeWithProducts>(recipe3Created, HttpStatus.CREATED);

        given(service.createRecipe(refEq(recipe3))).willReturn(res);

        mvc.perform(post("/recipes/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipe3))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).createRecipe(refEq(recipe3));

    }

    @Test
    public void updateRecipe()
            throws Exception {
        /* Исходные данные*/
        RecipeWithProducts recipe3 = new RecipeWithProducts();
        recipe3.setName("Recipe 3");
        recipe3.setCost(150);

        ArrayList<RecipeIngredientWithProductInfo> recipeIngredients = new ArrayList<>();
        RecipeIngredientWithProductInfo oiInfo1 = new RecipeIngredientWithProductInfo();
        ProductInfo prod = new ProductInfo();
        prod.setName("Ololo");
        oiInfo1.setProduct(prod);
        oiInfo1.setCount(50);
        oiInfo1.setId(16);
        recipeIngredients.add(oiInfo1);

        RecipeIngredientWithProductInfo oiInfo2 = new RecipeIngredientWithProductInfo();
        ProductInfo prod2 = new ProductInfo();
        prod2.setName("Ololo2");
        oiInfo2.setProduct(prod2);
        oiInfo2.setCount(10);
        recipeIngredients.add(oiInfo2);

        /* Результат*/
        RecipeWithProducts recipe3Created = new RecipeWithProducts();
        recipe3Created.setName("Recipe 3");
        recipe3Created.setCost(150);

        ArrayList<RecipeIngredientWithProductInfo> recipeIngredientsreated = new ArrayList<>();
        RecipeIngredientWithProductInfo oiInfo1reated = new RecipeIngredientWithProductInfo();
        ProductInfo prodreated  = new ProductInfo();
        prodreated.setName("Ololo");
        prodreated.setId(15);
        oiInfo1reated.setProduct(prod);
        oiInfo1reated.setCount(50);
        oiInfo1reated.setId(16);
        recipeIngredientsreated.add(oiInfo1);

        RecipeIngredientWithProductInfo oiInfo2reated = new RecipeIngredientWithProductInfo();
        ProductInfo prod2reated = new ProductInfo();
        prod2reated.setName("Ololo2");
        prod2reated.setId(17);
        oiInfo2reated.setProduct(prod2);
        oiInfo2reated.setCount(10);
        oiInfo2reated.setId(18);
        recipeIngredientsreated.add(oiInfo2);

        recipe3Created.setRecipeIngredients(recipeIngredients);
        recipe3Created.setId(19);

        ResponseEntity<RecipeWithProducts> res = new ResponseEntity<RecipeWithProducts>(recipe3Created, HttpStatus.OK);

        given(service.updateRecipe(refEq(recipe3), refEq(21))).willReturn(res);

        mvc.perform(put("/recipes/21")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.asJsonString(recipe3))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).updateRecipe(refEq(recipe3), refEq(21));
    }
}
