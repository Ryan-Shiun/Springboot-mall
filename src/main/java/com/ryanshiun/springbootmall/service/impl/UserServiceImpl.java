package com.ryanshiun.springbootmall.service.impl;

import com.ryanshiun.springbootmall.dao.UserDao;
import com.ryanshiun.springbootmall.dto.UserRegisterRequest;
import com.ryanshiun.springbootmall.model.User;
import com.ryanshiun.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
