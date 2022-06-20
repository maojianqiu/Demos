package com.vae.demo03.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.URLUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.Iterator;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    /**
     *
     * @param authentication 当前用户信息
     * @param object
     * @param configAttributes 平台权限集合
     */
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //当接口未被配置资源时直接放行
//        if (CollUtil.isEmpty(configAttributes)) {
//            return;
//        }

        String url = ((FilterInvocation) object).getRequestUrl();
        String path = URLUtil.getPath(url);

        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {

            if (pathMatcher.match( grantedAuthority.getAuthority(),path)) {
                //若匹配到一个权限，则直接返回
                return;
            }
        }




//        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
//
//        while (iterator.hasNext()) {
//            ConfigAttribute configAttribute = iterator.next();
//            //将访问所需资源或用户拥有资源进行比对
//            String needAuthority = configAttribute.getAttribute();
//
//            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
//                if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
//                    //若匹配到一个权限，则直接返回
//                    return;
//                }
//            }
//        }
        //若没有权限则提示
        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
