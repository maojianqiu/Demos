package org.vae.configuration.spring.security;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.vae.configuration.property.CookieConfig;
import org.vae.utility.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 登录参数序列化
 * @author 武汉思维跳跃科技有限公司
 */
//身份认证Authentication确认有用户，和授权Authorization是不同的
/*
* 职责也就非常明确——处理所有HTTP Request和Response对象，并将其封装成AuthenticationMananger可以处理的Authentication。
* (AbstractAuthenticationProcessingFilter将HttpServletRequest包装成了Authentication对象与核心的AuthenticationManager进行交互。这样的设计可以使AuthenticationManager不感知外部的Web环境，从而使Security不仅可以支持Web应用，同时也可以被所有Java应用进行使用——只要客制化外部参与并将其封装成Authentication与AuthenticationManager的进行身份验证。)
*
* 1.doFilter中通过调用自己的
* 2.attemptAuthentication方法，但并不进行身份验证的逻辑处理，而是委托AuthenticationManager去完成相关的身份验证流程。
* */

public class RestLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(RestLoginAuthenticationFilter.class);

    public RestLoginAuthenticationFilter() {
        //1.匹配URL和Method
        super(new AntPathRequestMatcher("/api/user/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try (InputStream is = request.getInputStream()) {
            //从请求中获取参数
            //AuthenticationBean 自定义的，用于接收参数
            AuthenticationBean authenticationBean = JsonUtil.toJsonObject(is, AuthenticationBean.class);
            request.setAttribute(TokenBasedRememberMeServices.DEFAULT_PARAMETER, authenticationBean.isRemember());
            //我不知道用户名密码是不是对的，所以构造一个未认证的Token先
            authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.getUserName(), authenticationBean.getPassword());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        //顺便把请求和Token存起来（这里是未认证的凭据，交给 AuthenticationManager 进行授权之后就变成认证的，是通过 new 一个新的来创建）
        setDetails(request, authRequest);
        //Token给谁处理呢？当然是给当前的 AuthenticationManager 喽
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    void setUserDetailsService(UserDetailsService userDetailsService) {
        RestTokenBasedRememberMeServices tokenBasedRememberMeServices = new RestTokenBasedRememberMeServices(CookieConfig.getName(), userDetailsService);
        tokenBasedRememberMeServices.setTokenValiditySeconds(CookieConfig.getInterval());
        setRememberMeServices(tokenBasedRememberMeServices);
    }

    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
