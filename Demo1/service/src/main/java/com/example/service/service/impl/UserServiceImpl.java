package com.example.service.service.impl;

import com.example.dao.dao.UserDao;
import com.example.entity.User;
import com.example.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryService() {
        return userDao.queryUser();
    }
}
