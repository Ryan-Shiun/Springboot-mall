package com.ryanshiun.springbootmall.service;

import com.ryanshiun.springbootmall.dao.ProductQueryParams;
import com.ryanshiun.springbootmall.dto.ProductRequest;
import com.ryanshiun.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);

}
