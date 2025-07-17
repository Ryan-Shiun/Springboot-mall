package com.ryanshiun.springbootmall.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
}
