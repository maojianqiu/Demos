package com.vae.dao;


import com.vae.domain.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户与角色关系管理自定义Dao
 * Created by macro on 2018/10/8.
 */
public interface UmsUserRoleRelationDao {

    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(@Param("username") String username);

    List<UmsResource> getAllResource();
}
