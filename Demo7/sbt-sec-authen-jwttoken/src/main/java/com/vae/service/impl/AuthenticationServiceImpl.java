package com.vae.service.impl;

import com.vae.base.SystemCode;
import com.vae.bo.AuthenticationUserDetails;
import com.vae.dao.UserMapper;
import com.vae.domain.User;
import com.vae.domain.enums.RoleEnum;
import com.vae.service.AuthenticationService;
import com.vae.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 武汉思维跳跃科技有限公司
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public AuthenticationUserDetails loadUserByUsername(String username) {
        //获取用户信息
        User user = getUserByUserName(username);
        if (user != null) {
            List<String> resourceList = getResourceList(user.getRole());
            return new AuthenticationUserDetails(user, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    public List<String> getResourceList(Integer roleId) {
        List<String> roles = new ArrayList<>();
        roles.add(RoleEnum.fromCode(roleId).getRoleName());
        return roles;
    }


    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                LOGGER.info(userDetails.getUsername(), "密码不正确");
                return null;
            }
            if (!userDetails.isEnabled()) {
                LOGGER.info(userDetails.getUsername(), "帐号已被禁用");
                return null;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }
}
