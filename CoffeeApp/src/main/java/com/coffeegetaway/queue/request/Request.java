package com.coffeegetaway.queue.request;

import com.coffee.model.house.ProductInfo;
import jdk.nashorn.internal.codegen.types.Type;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;

public class Request {
    HttpEntity<?> request;
    String url;
    String class_name;
    HttpMethod method;
    String ref_type = "";

    public Request(String url, HttpEntity<?> request,  HttpMethod method, Class<?> class_name) {
        this.request = request;
        this.url = url;
        this.class_name = class_name.toString();
        this.method = method;
    }

    public Request(String url, HttpEntity<?> request,  HttpMethod method, ParameterizedTypeReference<?> ref_type) {
        this.request = request;
        this.url = url;
        this.ref_type = ref_type.getClass().toString();
        this.method = method;
    }


    public ParameterizedTypeReference<?> getParameterizedTypeReference() {
        return new ParameterizedTypeReference<List<ProductInfo>>(){};
    }

    public void setParameterizedTypeReference(ParameterizedTypeReference<?> ref_type) {
        this.ref_type = ref_type.getType().getTypeName().toString();
    }

    public HttpEntity<?> getRequest() {
        return request;
    }

    public void setRequest(HttpEntity<?> request) {
        this.request = request;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class getClassName() {
        try {
            return Class.forName(class_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Object.class;
    }

    public void setClassName(Class class_name) {
        this.class_name = class_name.toString();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

}
