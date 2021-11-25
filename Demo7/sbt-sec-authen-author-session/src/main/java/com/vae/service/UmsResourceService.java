package com.vae.service;


import com.vae.domain.model.UmsResource;

import java.util.List;

/**
 * 后台资源管理Service
 * Created by macro on 2020/2/2.
 */
public interface UmsResourceService {

    /**
     * 查询全部资源
     */
    List<UmsResource> listAll();
}
