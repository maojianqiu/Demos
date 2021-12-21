package com.vae.demo01.mapper;


import com.vae.demo01.entity.User;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Select("select * from users4-2 where username = #{username}")
    User getUserInfo(String username);
}
