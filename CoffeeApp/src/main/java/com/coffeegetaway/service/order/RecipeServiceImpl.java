package com.coffeegetaway.service.order;

import com.coffee.model.house.ProductInfo;
import com.coffee.model.order.recipe.RecipeInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipe.RecipeWithProducts;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeIngredientWithProductInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private String default_urlTarget = "http://localhost:8081/recipes/";

    @Override
    public RecipeWithIngredientsInfo findRecipeById(Integer id) {

        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        RecipeWithIngredientsInfo result = restTemplate.getForObject(urlTarget, RecipeWithIngredientsInfo.class);
        return result;
    }

    @Override
    public List<RecipeWithIngredientsInfo> allRecipes() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<RecipeWithIngredientsInfo>> result = restTemplate.exchange(default_urlTarget,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecipeWithIngredientsInfo>>(){});
        return result.getBody();
    }

    @Override
    public void deleteRecipe(Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @Override
    public ResponseEntity<RecipeWithIngredientsInfo> updateRecipe(RecipeInfo recipeInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<RecipeInfo> request = new HttpEntity<>(recipeInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RecipeWithIngredientsInfo> result = restTemplate.exchange(urlTarget,
                HttpMethod.PUT,
                request,
                RecipeWithIngredientsInfo.class);
        return result;
    }

    @Override
    public ResponseEntity<RecipeWithProducts> createRecipe(RecipeWithProducts recipeInfo) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<ProductInfo>> products_result = restTemplate.exchange("http://localhost:8080/products/", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductInfo>>(){});
        List<ProductInfo> products = products_result.getBody();

        for (RecipeIngredientWithProductInfo riInfo:recipeInfo.getRecipeIngredients()){
            if (riInfo.getProduct().getId() == null) {
                for(ProductInfo pInfo: products) {
                    if (pInfo.getName().replaceAll("\\s","").equals(riInfo.getProduct().getName().replaceAll("\\s",""))) {
                        riInfo.getProduct().setId(pInfo.getId());
                    }
                }

                if (riInfo.getProduct().getId() == null) {
                    ProductInfo productInfo = new ProductInfo();
                    productInfo.setName(riInfo.getProduct().getName());

                    restTemplate = new RestTemplate();

                    HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo);
                    ProductInfo result = restTemplate.postForObject("http://localhost:8080/products/", request, ProductInfo.class);
                    if (result == null)
                        return new ResponseEntity<RecipeWithProducts>((RecipeWithProducts) null, HttpStatus.NOT_ACCEPTABLE);
                    riInfo.getProduct().setId(result.getId());
                    products.add(result);
                }
            }
        }

        restTemplate = new RestTemplate();

        HttpEntity<RecipeWithIngredientsInfo> request = new HttpEntity<>(buildRecipeWithIngredientsInfo(recipeInfo));
        RecipeWithIngredientsInfo result = restTemplate.postForObject(default_urlTarget, request, RecipeWithIngredientsInfo.class);
        if (result == null)
            return new ResponseEntity<>((RecipeWithProducts) null, HttpStatus.NOT_ACCEPTABLE);


        return new ResponseEntity<RecipeWithProducts>(buildRecipeWithProducts(result, products), HttpStatus.CREATED);
    }

    private RecipeWithIngredientsInfo buildRecipeWithIngredientsInfo(RecipeWithProducts recipeInfo) {
        RecipeWithIngredientsInfo recipeWithIngredientsInfo = new RecipeWithIngredientsInfo();
        recipeWithIngredientsInfo.setId(recipeInfo.getId());
        recipeWithIngredientsInfo.setCost(recipeInfo.getCost());
        recipeWithIngredientsInfo.setName(recipeInfo.getName());

        ArrayList<OnlyIngredientInfo> riList = new ArrayList<>();
        for (RecipeIngredientWithProductInfo riInfo:recipeInfo.getRecipeIngredients()) {
            OnlyIngredientInfo oiInfo = new OnlyIngredientInfo();
            oiInfo.setCount(riInfo.getCount());
            oiInfo.setProductId(riInfo.getProduct().getId());
            oiInfo.setId(riInfo.getId());
            riList.add(oiInfo);
        }

        recipeWithIngredientsInfo.setRecipeIngredients(riList);
        return recipeWithIngredientsInfo;
    }

    private RecipeWithProducts buildRecipeWithProducts(RecipeWithIngredientsInfo recipeInfo, List<ProductInfo> products) {
        RecipeWithProducts recipeWithProducts = new RecipeWithProducts();
        recipeWithProducts.setId(recipeInfo.getId());
        recipeWithProducts.setCost(recipeInfo.getCost());
        recipeWithProducts.setName(recipeInfo.getName());

        ArrayList <RecipeIngredientWithProductInfo> riList = new ArrayList<>();
        for (OnlyIngredientInfo riInfo:recipeInfo.getRecipeIngredients()) {
            RecipeIngredientWithProductInfo oiInfo = new RecipeIngredientWithProductInfo();
            oiInfo.setCount(riInfo.getCount());
            oiInfo.setProduct(findProductById(products, riInfo.getProductId()));
            oiInfo.setId(riInfo.getId());
            riList.add(oiInfo);
        }

        recipeWithProducts.setRecipeIngredients(riList);
        return recipeWithProducts;
    }

    ProductInfo findProductById(List<ProductInfo> products, Integer productId) {
        for (ProductInfo productInfo:products) {
            if (productInfo.getId() == productId)
                return productInfo;
        }
        return null;
    }
}
