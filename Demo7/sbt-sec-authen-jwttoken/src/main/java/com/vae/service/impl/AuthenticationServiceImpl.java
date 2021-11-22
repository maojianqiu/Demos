package com.vae.service.impl;

import com.vae.bo.AuthenticationUserDetails;
import com.vae.service.AuthenticationService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 武汉思维跳跃科技有限公司
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    @Override
    public AuthenticationUserDetails loadUserByUsername(String username) {
        //获取用户信息
        User admin = getAdminByUsername(username);
        if (admin != null) {
            List<String> resourceList = getResourceList(admin.getId());
            System.out.println("UmsAdminServiceImpl loadUserByUsername()  : " + "resourceList=" + resourceList.toString());

            return new AuthenticationUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public User getAdminByUsername(String username) {

        return null;
    }

    public List<String> getResourceList(Long adminId) {

        return null;
    }
}
