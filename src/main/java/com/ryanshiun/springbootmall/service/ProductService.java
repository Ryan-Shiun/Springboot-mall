package com.ryanshiun.springbootmall.service;

import com.ryanshiun.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
}
