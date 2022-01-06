package com.vae.demo03.base;

import cn.hutool.core.util.URLUtil;
import com.vae.demo03.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 动态权限数据源，用于获取动态权限规则
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    //存储当前平台所有权限列表
    private static Map<String, ConfigAttribute> configAttributeMap = null;

    //注入加载平台权限的业务类
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //加载平台权限
    public void loadDataSource() {
        configAttributeMap = myUserDetailsService.loadDataSource();
    }

    //清除平台权限，如果权限又新增了，那么需要清空这里的权限
    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    //根据当前访问请求判断所需要的权限集合
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //判断是否有平台权限列表
        if (configAttributeMap == null) this.loadDataSource();

        List<ConfigAttribute> configAttributes = new ArrayList<>();
        //获取当前访问的路径
        String url = ((FilterInvocation) o).getRequestUrl();
        String path = URLUtil.getPath(url);

        //路径匹配器，路径匹配的工具
        PathMatcher pathMatcher = new AntPathMatcher();
        //拿到当前所有的权限
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        //获取访问该路径所需所有资源，资源是可包含的
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        System.out.println("DynamicSecurityMetadataSource : " + "configAttributes=" + configAttributes.toString());
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
