package com.ryanshiun.springbootmall.service.impl;

import com.ryanshiun.springbootmall.dao.OrderDao;
import com.ryanshiun.springbootmall.dao.ProductDao;
import com.ryanshiun.springbootmall.dao.impl.OrderDaoImpl;
import com.ryanshiun.springbootmall.dto.BuyItem;
import com.ryanshiun.springbootmall.dto.CreteOrderRequest;
import com.ryanshiun.springbootmall.model.OrderItem;
import com.ryanshiun.springbootmall.model.Product;
import com.ryanshiun.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    // 修改多張 SQL 表格要使用 rollback
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreteOrderRequest creteOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        // 先逐個去 product 撈商品價錢
        for (BuyItem buyItem : creteOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            // 將前端資訊 BuyItem 轉換成 OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
