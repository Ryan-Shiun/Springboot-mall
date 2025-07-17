package com.ryanshiun.springbootmall.controller;

import com.ryanshiun.springbootmall.dto.CreteOrderRequest;
import com.ryanshiun.springbootmall.model.Order;
import com.ryanshiun.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 創建訂單 (在帳號已經存在的情況下才可以創建訂單)
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreteOrderRequest creteOrderRequest) {
        Integer orderId = orderService.createOrder(userId, creteOrderRequest);

        // 回傳整筆訂單資訊給前端
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
