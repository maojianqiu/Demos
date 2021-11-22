package com.vae.service;


import com.vae.bo.AuthenticationUserDetails;

/**
 * @author 武汉思维跳跃科技有限公司
 */
public interface AuthenticationService {

    /**
     * 获取用户信息
     */
    AuthenticationUserDetails loadUserByUsername(String username);

}