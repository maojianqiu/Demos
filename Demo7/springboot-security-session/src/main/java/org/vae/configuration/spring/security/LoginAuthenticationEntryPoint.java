package org.vae.configuration.spring.security;

import com.mindskip.xzs.base.SystemCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录,当用户请求了一个受保护的资源，但是用户没有通过认证
 * @author 武汉思维跳跃科技有限公司
 */
@Component
public final class LoginAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public LoginAuthenticationEntryPoint() {
        super("/api/user/login");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        RestUtil.response(response, SystemCode.UNAUTHORIZED);
    }

}
