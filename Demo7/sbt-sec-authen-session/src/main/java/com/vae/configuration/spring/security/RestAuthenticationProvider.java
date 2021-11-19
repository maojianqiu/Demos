package com.vae.configuration.spring.security;


import com.vae.context.WebContext;
import com.vae.domain.enums.RoleEnum;
import com.vae.domain.enums.UserStatusEnum;
import com.vae.service.AuthenticationService;
import com.vae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 登录用户名密码验证
 *
 * @author :  武汉思维跳跃科技有限公司
 * Description :  身份验证
 */

@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final WebContext webContext;

    @Autowired
    public RestAuthenticationProvider(AuthenticationService authenticationService, UserService userService, WebContext webContext) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.webContext = webContext;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        //1.先看有沒有这个用户名
        com.vae.domain.User user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        //2.在进行用户名密码验证
        boolean result = authenticationService.authUser(user, username, password);
        if (!result) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        //3.有用户名且密码也是对的，在查看是不是启用状态的
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        if (UserStatusEnum.Disable == userStatusEnum) {
            throw new LockedException("用户被禁用");
        }

        //4.身份认证成功后，一定要将角色也加入进去，这里用的是 security 默认的角色格式 ROLE_ADMIN
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));

        User authUser = new User(user.getUserName(), user.getPassword(), grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(), authUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
