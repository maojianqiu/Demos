package com.vae.service.impl;

import com.vae.dao.UmsUserRoleRelationDao;
import com.vae.domain.model.UmsResource;
import com.vae.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 后台资源管理Service实现类
 * Created by macro on 2020/2/2.
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {
    @Autowired
    private UmsUserRoleRelationDao  userRoleRelationDao;


    @Override
    public List<UmsResource> listAll() {
        return userRoleRelationDao.getAllResource();
    }
}
