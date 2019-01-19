package com.coffeegetaway.service.order;

import com.coffee.model.house.ProductInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import com.coffee.model.order.recipe.RecipeWithProducts;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import com.coffee.model.order.recipeIngredient.RecipeIngredientWithProductInfo;
import com.coffeegetaway.queue.JQueue;
import com.coffeegetaway.queue.request.Request;
import com.coffeegetaway.service.auth.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private String default_urlTarget = "http://localhost:8081/recipes/";
    private Authorize auth = new Authorize("http://localhost:8080/auth", "getaway", "getaway-house");

    private JQueue queue = new JQueue();

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
    public ResponseEntity<RecipeWithProducts> updateRecipe(RecipeWithProducts recipeInfo, Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity entity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ProductInfo>> products_result = new ResponseEntity<>(HttpStatus.OK);

        try {
            products_result = restTemplate.exchange("http://localhost:8080/products/",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ProductInfo>>() {
                    });

        } catch (Exception e) {
            Request rq = new Request("http://localhost:8080/products/", entity, HttpMethod.GET, new ParameterizedTypeReference<List<ProductInfo>>(){});
            queue.push(rq);
//            TODO
//        } finally {
//            List<ProductInfo> products = products_result.getBody();
//            updateProducts(recipeInfo, products);
        }


        restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<RecipeWithIngredientsInfo> request = new HttpEntity<>(buildRecipeWithIngredientsInfo(recipeInfo));
        ResponseEntity<RecipeWithIngredientsInfo> result = new ResponseEntity<>(HttpStatus.OK);
        try {
            result = restTemplate.exchange(urlTarget,
                    HttpMethod.PUT,
                    request,
                    RecipeWithIngredientsInfo.class);

        } catch (Exception e) {
            Request rq = new Request(urlTarget, request, HttpMethod.PUT, RecipeWithIngredientsInfo.class);
            queue.push(rq);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RecipeWithProducts> createRecipe(RecipeWithProducts recipeInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<RecipeWithIngredientsInfo> request = new HttpEntity<>(buildRecipeWithIngredientsInfo(recipeInfo));
        RecipeWithIngredientsInfo result = restTemplate.postForObject(default_urlTarget, request, RecipeWithIngredientsInfo.class);
        if (result == null)
            return new ResponseEntity<>((RecipeWithProducts) null, HttpStatus.NOT_ACCEPTABLE);

        ResponseEntity<List<ProductInfo>> products_result = restTemplate.exchange("http://localhost:8080/products/", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductInfo>>(){});

        restTemplate = new RestTemplate();
        List<ProductInfo> products = products_result.getBody();

        if (!createProducts(recipeInfo, products))
            return new ResponseEntity<RecipeWithProducts>((RecipeWithProducts) null, HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<RecipeWithProducts>(buildRecipeWithProducts(result, products), HttpStatus.CREATED);
    }

    private Boolean createProducts(RecipeWithProducts recipeInfo, List<ProductInfo> products){

        for (RecipeIngredientWithProductInfo riInfo:recipeInfo.getRecipeIngredients()){
            if (riInfo.getProduct().getId() == null) {
                if(!createProducts(riInfo, products)){
                    return false;
                }
            }
        }
        return true;
    }
    private Boolean updateProducts(RecipeWithProducts recipeInfo, List<ProductInfo> products){

        for (RecipeIngredientWithProductInfo riInfo:recipeInfo.getRecipeIngredients()){
            if (riInfo.getProduct().getId() == null) {
                if(!createProducts(riInfo, products)){
                    return false;
                }
            } else {
                if(!updateProducts(riInfo, products)){
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean updateProducts(RecipeIngredientWithProductInfo riInfo, List<ProductInfo> products) {
        Boolean changed = false;
        ProductInfo product = findProductById(products, riInfo.getProduct().getId());
        if (product != null &&
                product.getName().replaceAll("\\s","").equals(riInfo.getProduct().getName().replaceAll("\\s",""))){
            changed = true;
        }

        if (changed) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setName(riInfo.getProduct().getName());

            ProductInfo result = updateProduct(riInfo.getProduct().getId(), productInfo);
            if (result == null) {
                return false;
            }

        }
        return true;
    }
    private Boolean createProducts(RecipeIngredientWithProductInfo riInfo, List<ProductInfo> products) {
        for(ProductInfo pInfo: products) {
            if (pInfo.getName().replaceAll("\\s","")
                    .equals(riInfo.getProduct().getName().replaceAll("\\s",""))) {
                riInfo.getProduct().setId(pInfo.getId());
            }
        }

        if (riInfo.getProduct().getId() == null) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setName(riInfo.getProduct().getName());

            ProductInfo result = createProduct(productInfo);
            if (result == null) {
                return false;
            }

            riInfo.getProduct().setId(result.getId());
            products.add(result);
        }
        return true;
    }

    private ProductInfo createProduct(ProductInfo productInfo ) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo);
        ProductInfo result = restTemplate.postForObject("http://localhost:8080/products/", request, ProductInfo.class);
        if (result == null)
            return null;
        return result;
    }
    private ProductInfo updateProduct(Integer id, ProductInfo productInfo) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductInfo> result = new ResponseEntity(HttpStatus.OK);
        HttpEntity<ProductInfo> request = new HttpEntity<>(productInfo, headers);
        try{
            result = restTemplate.exchange("http://localhost:8080/products/" + id.toString(),
                    HttpMethod.PUT,
                    request,
                    ProductInfo.class);
        } catch (Exception e) {
            Request rq = new Request("http://localhost:8080/products/", request, HttpMethod.PUT, ProductInfo.class);
            queue.push(rq);
        }
        return result.getBody();
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

    private ProductInfo findProductById(List<ProductInfo> products, Integer productId) {
        for (ProductInfo productInfo:products) {
            if (productInfo.getId() == productId)
                return productInfo;
        }
        return null;
    }
}
