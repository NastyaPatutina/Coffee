package com.coffee.service.product;

import com.coffee.entity.Product;
import com.coffee.model.house.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface ProductService {
    @Nonnull
    List<ProductInfo> findAllProducts();

    @Nullable
    ProductInfo findProductById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    Product saveAndAddToStorage(ProductInfo product);

    Product save(ProductInfo product);
}
