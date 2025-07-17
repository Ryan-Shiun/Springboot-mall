package com.ryanshiun.springbootmall.service;

import com.ryanshiun.springbootmall.dto.CreteOrderRequest;
import com.ryanshiun.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreteOrderRequest creteOrderRequest);
}
