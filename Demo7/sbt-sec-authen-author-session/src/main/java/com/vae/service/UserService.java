package com.vae.service;

import com.github.pagehelper.PageInfo;
import com.vae.domain.model.User;
import com.vae.domain.other.KeyValue;
import com.vae.viewmodel.UserPageRequestVM;

import java.util.List;

/**
 * @author 武汉思维跳跃科技有限公司
 */
public interface UserService {


    /**
     * getUserByUserName
     *
     * @param username username
     * @return User
     */
    User getUserByUserName(String username);


}
