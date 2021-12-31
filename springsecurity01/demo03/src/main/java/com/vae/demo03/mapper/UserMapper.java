package com.vae.demo03.mapper;


import com.vae.demo03.entity.User;


public interface UserMapper {

    User getUserInfo(String username);

    User getUserInfoByPhone(String phone);
}
