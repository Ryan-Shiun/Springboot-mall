package com.ryanshiun.springbootmall.service;

import com.ryanshiun.springbootmall.dto.ProductRequest;
import com.ryanshiun.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
}
