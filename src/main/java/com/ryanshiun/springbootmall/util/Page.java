package com.ryanshiun.springbootmall.util;

import lombok.Data;

import java.util.List;

// 專門處理分頁
@Data
public class Page<T> {

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;
}
