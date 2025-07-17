package com.ryanshiun.springbootmall.service;

import com.ryanshiun.springbootmall.dto.CreateOrderRequest;
import com.ryanshiun.springbootmall.dto.OrderQueryParams;
import com.ryanshiun.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
