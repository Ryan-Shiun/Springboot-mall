package com.ryanshiun.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String email;
    // 隱藏密碼不傳回給前端
    @JsonIgnore
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;
}
