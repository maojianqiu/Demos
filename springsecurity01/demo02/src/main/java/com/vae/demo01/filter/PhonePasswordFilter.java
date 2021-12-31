package com.vae.demo01.filter;

import com.vae.demo01.base.PhonePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: PhonePasswordFilter <br>
 * date: 2021/12/27 21:44 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */
public class PhonePasswordFilter extends AbstractAuthenticationProcessingFilter {

    //定义前端 form 表单传数值时，存到request中的参数名称，一般是 form 表单中的标签的 name 值。
    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private boolean postOnly = true;

    public PhonePasswordFilter(String defaultFilterProcessesUrl) {
        //1.匹配URL和Method
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //1.判断是不是 post 方法，这一步是校验，不是必须的
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //2.从request中拿取name为“phone”的参数值，name为“password”的参数值
        String phone = request.getParameter(this.SPRING_SECURITY_FORM_PHONE_KEY);
        phone = (phone != null) ? phone : "";
        phone = phone.trim();
        String password = request.getParameter(this.SPRING_SECURITY_FORM_PASSWORD_KEY);
        password = (password != null) ? password : "";
        //3.封装一个 Authentication ，这里需要自定义
        PhonePasswordAuthenticationToken authRequest = new PhonePasswordAuthenticationToken(phone, password);
        //4.这里是设置细节信息
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    //调用父类的 setDetails() 方法，设置细节信息
    protected void setDetails(HttpServletRequest request, PhonePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
