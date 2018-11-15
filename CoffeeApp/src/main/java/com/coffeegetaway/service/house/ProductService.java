package com.coffeegetaway.service.house;

import com.coffee.model.house.ProductInfo;
import com.coffee.model.order.recipe.RecipeWithIngredientsInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ProductInfo findProductById(Integer id);

    List<ProductInfo> allProducts();

    void deleteProduct(Integer id);

    ResponseEntity<ProductInfo> updateProduct(ProductInfo productInfo, Integer id);

    ResponseEntity<ProductInfo> createProduct(ProductInfo productInfo);
}
