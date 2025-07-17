package com.ryanshiun.springbootmall.service;

import com.ryanshiun.springbootmall.dto.CreteOrderRequest;
import com.ryanshiun.springbootmall.dto.OrderQueryParams;
import com.ryanshiun.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreteOrderRequest creteOrderRequest);

    Order getOrderById(Integer orderId);
}
