package com.vae.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vae.domain.model.User;
import com.vae.domain.other.KeyValue;
import com.vae.service.UserService;
import com.vae.viewmodel.UserPageRequestVM;
import com.vae.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 武汉思维跳跃科技有限公司
 */
@Service
public class UserServiceImpl  implements UserService {

    private final static String CACHE_NAME = "xzs:user";
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }



    @Override
//    @Cacheable(value = CACHE_NAME, key = "#username", unless = "#result == null")
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }


}
