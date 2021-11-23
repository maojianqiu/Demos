package com.vae.service;


import com.vae.bo.AuthenticationUserDetails;
import com.vae.domain.User;

/**
 * @author 武汉思维跳跃科技有限公司
 */
public interface AuthenticationService {

    /**
     * 获取用户信息
     */
    AuthenticationUserDetails loadUserByUsername(String username);


    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);


    /**
     * getUserByUserName
     *
     * @param username username
     * @return User
     */
    User getUserByUserName(String username);

}