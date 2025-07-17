package com.ryanshiun.springbootmall.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    private String orderId;
    private String userId;
    private Integer totalAmount;
    private Date createDate;
    private Date lastModifiedDate;
    private List<OrderItem> orderItemList;
}
