package com.vae.demo01.mapper;


import com.vae.demo01.entity.User;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {

    User getUserInfo(String username);

    User getUserInfoByPhone(String phone);
}
