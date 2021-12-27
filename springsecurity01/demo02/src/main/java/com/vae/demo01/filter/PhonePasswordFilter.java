package com.vae.demo01.filter;

import org.springframework.beans.factory.annotation.Value;
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

//    @Value("${security.phone-url}")
//    private static String PHONE_URL;

    public PhonePasswordFilter(String defaultFilterProcessesUrl) {
        //1.匹配URL和Method
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        System.out.println("---------PhonePasswordFilter-----------");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
