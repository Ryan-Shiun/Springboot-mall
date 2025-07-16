package com.ryanshiun.springbootmall.dao;

import com.ryanshiun.springbootmall.dto.UserRegisterRequest;
import com.ryanshiun.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
}
