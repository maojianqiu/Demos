package com.example.dao.dao;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserDao {
    List<User> queryUser();
}

