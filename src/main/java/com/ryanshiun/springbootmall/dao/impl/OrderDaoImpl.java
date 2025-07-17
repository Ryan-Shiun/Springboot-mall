package com.ryanshiun.springbootmall.dao.impl;

import com.ryanshiun.springbootmall.dao.OrderDao;
import com.ryanshiun.springbootmall.dto.OrderQueryParams;
import com.ryanshiun.springbootmall.model.Order;
import com.ryanshiun.springbootmall.model.OrderItem;
import com.ryanshiun.springbootmall.rowmapper.OrderItemRowMapper;
import com.ryanshiun.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "SELECT count(*) FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        sql = addFilteringSql(sql, map, orderQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        sql = addFilteringSql(sql, map, orderQueryParams);

        // 排序 (不讓前端修改所以寫死)
        sql += " ORDER BY created_date DESC";

        // 分頁
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return orderList;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, "
                + "p.product_name, p.image_url "
                + "FROM order_item AS oi "
                + "LEFT JOIN product AS p ON oi.product_id = p.product_id "
                + "WHERE oi.order_id = :orderId";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        return namedParameterJdbcTemplate.query(sql, params, new OrderItemRowMapper());
    }

    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date "
                + "FROM `order` WHERE order_id = :orderId";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, params, new OrderRowMapper());
        return orderList.isEmpty() ? null : orderList.get(0);
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) "
                + "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("totalAmount", totalAmount);
        Date now = new Date();
        params.put("createdDate", now);
        params.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) "
                + "VALUES (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem item = orderItemList.get(i);
            batchParams[i] = new MapSqlParameterSource()
                    .addValue("orderId", orderId)
                    .addValue("productId", item.getProductId())
                    .addValue("quantity", item.getQuantity())
                    .addValue("amount", item.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
        if (orderQueryParams.getUserId() != null) {
            sql += " AND user_id = :userId ";
            map.put("userId", orderQueryParams.getUserId());
        }
        return sql;
    }
}
