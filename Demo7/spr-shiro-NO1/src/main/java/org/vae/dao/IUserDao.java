package org.vae.dao;

import org.apache.ibatis.annotations.Param;
import org.vae.model.UserVO;


public interface IUserDao {
    void addUser(@Param("user") UserVO user);
}
