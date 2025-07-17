package com.ryanshiun.springbootmall.service.impl;

import com.ryanshiun.springbootmall.dao.OrderDao;
import com.ryanshiun.springbootmall.dao.ProductDao;
import com.ryanshiun.springbootmall.dao.UserDao;
import com.ryanshiun.springbootmall.dao.impl.OrderDaoImpl;
import com.ryanshiun.springbootmall.dto.BuyItem;
import com.ryanshiun.springbootmall.dto.CreteOrderRequest;
import com.ryanshiun.springbootmall.model.Order;
import com.ryanshiun.springbootmall.model.OrderItem;
import com.ryanshiun.springbootmall.model.Product;
import com.ryanshiun.springbootmall.model.User;
import com.ryanshiun.springbootmall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);
        return order;
    }

    // 修改多張 SQL 表格要使用 rollback
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreteOrderRequest creteOrderRequest) {

        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("user id not found ");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        // 先逐個去 product 撈商品價錢
        for (BuyItem buyItem : creteOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 Product 是否存在、庫存是否足夠
            if (product == null) {
                log.warn("product id not found", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("product stock not enough");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除庫存商品
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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
