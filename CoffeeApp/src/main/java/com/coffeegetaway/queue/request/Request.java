package com.coffeegetaway.queue.request;

import com.coffee.model.house.ProductInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import jdk.nashorn.internal.codegen.types.Type;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;

public class Request {
    HttpHeaders headers;
    RecipeWithIngredientsInfo requestRecipeBody = null;
    ProductInfo requestProductBody = null;
    String url;
    HttpMethod method;

    public Request(String url,  HttpMethod method, RecipeWithIngredientsInfo request,  HttpHeaders headers) {
        this.requestRecipeBody = request;
        this.headers = headers;
        this.url = url;
        this.method = method;
    }

    public Request(String url, HttpMethod method, ProductInfo request,  HttpHeaders headers) {
        this.requestProductBody = request;
        this.headers = headers;
        this.url = url;
        this.method = method;
    }

    public HttpEntity<?> getRequest() {
        return new HttpEntity<>(getRequestBody(), headers);
    }

    private Object getRequestBody(){
        if (requestProductBody != null) {
            return requestProductBody;
        }
        if (requestRecipeBody != null) {
            return requestRecipeBody;
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public Class getClassName() {
        if (requestProductBody != null) {
            return ProductInfo.class;
        }
        if (requestRecipeBody != null) {
            return RecipeWithIngredientsInfo.class;
        }
        return Object.class;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public ParameterizedTypeReference getParameterizedTypeReference() {
        return new ParameterizedTypeReference<List<ProductInfo>>(){};
    }
}
