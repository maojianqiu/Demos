package com.vae.demo03.service;

import com.vae.demo03.entity.User;
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
    UserMapper userMapper;

    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserInfo(username);

        if(user == null){
            throw new UsernameNotFoundException("用户不存在！");

        }
        List<UmsResource> resourceList = getResourceList(user.getId());

        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));

        return user;
    }

    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        User user = userMapper.getUserInfoByPhone(phone);

        if(user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }

        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));

        return user;
    }

    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList;
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        return resourceList;
    }

    //自定义的切割权限字符串的方法
    public static List<GrantedAuthority> generateAuthorityList(String authority) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList();
        String[] roles = authority.split(":");

        if(roles != null || "".equals(roles)){
            for(String role : roles) {
                //把每个角色都封装成 SimpleGrantedAuthority 对象
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }


        return grantedAuthorities;
    }

}
