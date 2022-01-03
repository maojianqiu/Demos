package com.vae.demo03.service;

import com.vae.demo03.dao.UmsAdminRoleRelationDao;
import com.vae.demo03.entity.UmsAdmin;
import com.vae.demo03.entity.UmsAdminExample;
import com.vae.demo03.entity.UmsResource;
import com.vae.demo03.entity.User;
import com.vae.demo03.mapper.UmsAdminMapper;
import com.vae.demo03.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * description: MyUserDetailsService <br>
 * date: 2021/12/21 22:12 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UmsAdminMapper umsAdminMapper;

    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //使用 mybatis 的 Criteria 操作数据库
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin>  lists = umsAdminMapper.selectByExample(umsAdminExample);
        if(lists.size() < 0){
            throw new UsernameNotFoundException("用户不存在！");
        }
        UmsAdmin umsAdmin = lists.get(0);

        //设计到表关联获取，自己写 SQL，因为使用的是角色、资源、用户的授权模式，用户有对应的角色，查询到角色后在查询角色对应的权限
        List<UmsResource> resourceList = getResourceList(umsAdmin.getId());

        User user = new User(umsAdmin,resourceList);

        return user;
    }

    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        //使用 mybatis 的 Criteria 操作数据库
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andPhoneEqualTo(phone);
        List<UmsAdmin>  lists = umsAdminMapper.selectByExample(umsAdminExample);
        if(lists.size() < 0){
            throw new UsernameNotFoundException("用户不存在！");
        }
        UmsAdmin umsAdmin = lists.get(0);

        //设计到表关联获取，自己写 SQL，因为使用的是角色、资源、用户的授权模式，用户有对应的角色，查询到角色后在查询角色对应的权限
        List<UmsResource> resourceList = getResourceList(umsAdmin.getId());

        User user = new User(umsAdmin,resourceList);

        return user;
    }

    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList;
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        return resourceList;
    }


}
