package com.vae.configuration.spring.security;

import com.vae.base.SystemCode;
import com.vae.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登出
 *
 * @author 武汉思维跳跃科技有限公司
 */
@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RestLogoutSuccessHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
//        if (null != springUser) {
//            User user = userService.getUserByUserName(springUser.getUsername());
//            UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
//            userEventLog.setContent(user.getUserName() + " 登出了学之思开源考试系统");
//            eventPublisher.publishEvent(new UserEvent(userEventLog));
//        }
        RestUtil.response(response, SystemCode.OK);
    }
}
