package com.vae.context;

import com.vae.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class WebContext {
    private static final String USER_ATTRIBUTES = "USER_ATTRIBUTES";

    public void setCurrentUser(UserDetails user) {
        RequestContextHolder.currentRequestAttributes().setAttribute(USER_ATTRIBUTES, user, RequestAttributes.SCOPE_REQUEST);
    }

    public UserDetails getCurrentUser() {
        //1.
        UserDetails user = (UserDetails) RequestContextHolder.currentRequestAttributes().getAttribute(USER_ATTRIBUTES, RequestAttributes.SCOPE_REQUEST);
        
        if (null != user) {
            return user;
        } else {
            //2.
            UserDetails springUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (null == springUser) {
                return null;
            }
//            user = userService.getUserByUserName(springUser.getUsername());
            if (null != user) {
                setCurrentUser(user);
                return user;
            }
            return null;
        }
    }
}
