package com.vae.demo03.filter;

import com.vae.demo03.base.DynamicAccessDecisionManager;
import com.vae.demo03.base.DynamicSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dynamic01FilterSecurityInterceptor extends FilterSecurityInterceptor {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

    @Value("${secure.ignored.urls}")
    private List<String> urls ;

    @Autowired
    public void setMyDynamicAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);

    }

    @Autowired
    public void setMyDynamicSecurityMetadataSource(DynamicSecurityMetadataSource dynamicAccessDecisionManager) {
        super.setSecurityMetadataSource(dynamicAccessDecisionManager);

    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return super.getSecurityMetadataSource();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        FilterInvocation fi =  new FilterInvocation(request, response, chain);

        System.out.println(fi.getRequest().getAttribute(FILTER_APPLIED));
        System.out.println(isObserveOncePerRequest());

        if ((fi.getRequest() != null)
                && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                && isObserveOncePerRequest()) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());

            logger.info("request " + fi.getRequestUrl() + " has already been security checking.");
        }
        else {
            logger.info("request " + fi.getRequestUrl() + " will be security checking.");

            // first time this request being called, so perform security checking
            if (fi.getRequest() != null && isObserveOncePerRequest()) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

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
