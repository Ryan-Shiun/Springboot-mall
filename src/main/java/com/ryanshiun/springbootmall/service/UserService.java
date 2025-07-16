package com.ryanshiun.springbootmall.service;

import com.ryanshiun.springbootmall.dto.UserRegisterRequest;
import com.ryanshiun.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
}
