package com.ryanshiun.springbootmall.dao;

import com.ryanshiun.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParams {

    private ProductCategory category;
    private String search;
    private String orderBy;
    private String sort;
}
