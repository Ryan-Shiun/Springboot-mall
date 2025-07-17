package com.ryanshiun.springbootmall.dao;

import com.ryanshiun.springbootmall.model.Order;
import com.ryanshiun.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    List<OrderItem> getOrderItemsByOrderId (Integer orderId);
}
