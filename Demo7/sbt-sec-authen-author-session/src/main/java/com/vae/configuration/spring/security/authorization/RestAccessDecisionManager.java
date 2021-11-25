package com.vae.configuration.spring.security.authorization;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 *
 * 注意：这个类的 decide （对比需要的权限和用户的权限）接口调动前，会在 Filter 的 beforeInvocation 中先调用 SecurityMetadataSource 自定义接口 getAttributes （获取所有动态权限），然后 传给 decide 接口 进行对比。
 *
 * Created by macro on 2020/2/7.
 */
@Component
public class RestAccessDecisionManager implements AccessDecisionManager {

    /*
    * 参数有当前用户和所有动态资源
    * */
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当接口未被配置资源时直接放行
        if (CollUtil.isEmpty(configAttributes)) {
            return;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();


        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();

            String needAuthority = configAttribute.getAttribute();

            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }
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
