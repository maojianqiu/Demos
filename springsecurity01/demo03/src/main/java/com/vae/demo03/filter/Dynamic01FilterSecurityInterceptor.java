package com.vae.demo03.filter;

import com.vae.demo03.base.DynamicAccessDecisionManager;
import com.vae.demo03.base.DynamicSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dynamic01FilterSecurityInterceptor extends FilterSecurityInterceptor {

    //FILTER_APPLIED：针对当前请求，当前 filter 是否执行过一次的标志 key。
    // 若没有执行过会将该常量作为 key 添加到 request Attribute 中，
    // 后续如果又进入当前 filter bean 中（一定执行过一次），判断 request 中有这个 key ，就直接跳过当前业务调取下一个过滤链。
    //思考：为什么加这个呢？
    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

    //白名单，配置在 applicatio.p 中，通过 @Value 获取。
    @Value("${secure.ignored.urls}")
    private List<String> urls ;

    //通过 set 方式注入 AccessDecisionManager Bean,直接注入到 super 中即可
    @Autowired
    public void setMyDynamicAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);

    }

    //通过 set 方式注入 SecurityMetadataSource Bean,直接注入到 super 中即可
    @Autowired
    public void setMyDynamicSecurityMetadataSource(DynamicSecurityMetadataSource dynamicAccessDecisionManager) {
        super.setSecurityMetadataSource(dynamicAccessDecisionManager);

    }

    //核心 doFilter()
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //封装对象，方便调用后续过滤器
        FilterInvocation fi =  new FilterInvocation(request, response, chain);

        System.out.println(fi.getRequest().getAttribute(FILTER_APPLIED));
        System.out.println(isObserveOncePerRequest());

        //如果当前请求已经调用过这个 filter bean，就直接执行后续过滤器
        if ((fi.getRequest() != null)
                && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                && isObserveOncePerRequest()) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());

            logger.info("request " + fi.getRequestUrl() + " has already been security checking.");
        }
        //如果当前请求没有调用过这个 filter bean，就需要执行业务
        else {
            logger.info("request " + fi.getRequestUrl() + " will be security checking.");

            // 没有执行过就是第一次，就需要修改状态为已执行过
            if (fi.getRequest() != null && isObserveOncePerRequest()) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            //OPTIONS请求直接放行
            if (httpRequest.getMethod().equals(HttpMethod.OPTIONS.toString())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            }
            //白名单请求直接放行
            PathMatcher pathMatcher = new AntPathMatcher();
            for (String path : urls) {
                if (pathMatcher.match(path, httpRequest.getRequestURI())) {
                    fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                    return;
                }
            }
            //到这里的都是需要权限的，所以需要进行授权认证
            InterceptorStatusToken token = super.beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                logger.info("request " + fi.getRequestUrl() + " .");
            }
            finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }
    }

}
