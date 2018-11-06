package com.coffee.helpers;

import com.coffee.entity.Product;
import com.coffee.model.ProductInfo;

import javax.annotation.Nonnull;

public class Builder {

    @Nonnull
    public static ProductInfo buildProductInfo(Product product) {
        ProductInfo info = new ProductInfo();
        info.setId(product.getId());
        info.setName(product.getName());
        return info;
    }

    @Nonnull
    public static Product buildProductByInfo(ProductInfo productInfo) {
        Product product = new Product();
        product.setName(productInfo.getName());
        product.setId(productInfo.getId());
        return product;
    }
}
