package com.ryanshiun.springbootmall.dao;

import com.ryanshiun.springbootmall.dto.ProductRequest;
import com.ryanshiun.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
