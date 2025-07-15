package com.ryanshiun.springbootmall.dao;

import com.ryanshiun.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
