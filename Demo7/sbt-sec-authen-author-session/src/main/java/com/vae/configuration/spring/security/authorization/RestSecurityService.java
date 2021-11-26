package com.vae.configuration.spring.security.authorization;

import com.vae.domain.model.UmsResource;
import com.vae.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态权限相关业务类，直接在配置文件荣添加一个 bean 就可以，（内部类）
 * Created by macro on 2020/2/7.
 */
@Component
public class RestSecurityService {

    private UmsResourceService umsResourceService;

    @Autowired
    public RestSecurityService(UmsResourceService umsResourceService) {
        this.umsResourceService = umsResourceService;
    }

    @Autowired


    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<UmsResource> resourceList = umsResourceService.listAll();
        for (UmsResource resource : resourceList) {
            map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
        }
        return map;
    }
}
