package com.ryanshiun.springbootmall.dao;

import com.ryanshiun.springbootmall.dto.ProductRequest;
import com.ryanshiun.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Integer countProduct(ProductQueryParams productQueryParams);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
    void updateStock(Integer orderId, Integer stock);
}
