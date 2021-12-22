package com.vae.demo01.service;

import com.vae.demo01.entity.User;
import com.vae.demo01.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * description: MyUserDetailsService <br>
 * date: 2021/12/21 22:12 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从持久层获取数据
        User user = userMapper.getUserInfo(username);

        if(user == null){
            //这是 security 自带的异常，会在过滤连中捕捉到
            throw new UsernameNotFoundException("用户不存在！");

        }

        //现在 user 对象里面的 roles 是 string 类型，并且用逗号隔开的，我们需要将 roles 设置到 authorities 类型中。
        //我们需要把他修改为 security 可识别的权限类型 ，GrantedAuthority 接口是 security 保存权限的类型，SimpleGrantedAuthority 是它的实现类，也是security 最常使用的。
        //AuthorityUtils.commaSeparatedStringToAuthorityList( String )是 security 提供的用于将逗号隔开的权限字符串切割成权限列表。
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));

        return user;
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
