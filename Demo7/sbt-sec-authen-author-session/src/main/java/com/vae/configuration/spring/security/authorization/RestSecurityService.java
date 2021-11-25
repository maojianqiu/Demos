package com.vae.configuration.spring.security.authorization;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务类，直接在配置文件荣添加一个 bean 就可以，（内部类）
 * Created by macro on 2020/2/7.
 */
public interface RestSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
