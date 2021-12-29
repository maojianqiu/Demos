package com.vae.demo01.base;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;

//继承 UsernamePasswordAuthenticationToken ，不继承 AbstractAuthenticationToken 的原因是后续会进行类型匹配，现在不需要那么高深的配置，所以先继承 UsernamePasswordAuthenticationToken
public class PhonePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private static final long serialVersionUID = 6339981734663827267L;

    //注意，Authentication 对象中，会需要密码信息（Credentials）、身份信息（Principal）、权限信息（Authorities）、细节信息（Details），但不一定都需要

//    private Object principal;
//    private Object credentials;

    public PhonePasswordAuthenticationToken(Object principal, Object credentials) {
        //调用父类的构造函数，创建一个未认证的 Token
        super(principal, credentials);
    }

    public PhonePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        //调用父类的构造函数，创建一个已认证的 Token
        super(principal, credentials, authorities);
    }

//    //这里是构建一个未认证的Authentication
//    public PhonePasswordAuthenticationToken(Object principal, Object credentials) {
//        super((Collection)null);
//        this.principal = principal;
//        this.credentials = credentials;
//        this.setAuthenticated(false);
//    }
//
//    //这里是构建一个已认证的Authentication
//    public PhonePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//        this.principal = principal;
//        this.credentials = credentials;
//        super.setAuthenticated(true);
//    }


//    //这里是拿取当前对象的密码信息
//    @Override
//    public Object getCredentials() {
//        return this.credentials;
//    }
//
//    //这里是拿取当前对象的身份信息
//    @Override
//    public Object getPrincipal() {
//        return this.principal;
//    }

    //这里也给当前对象加一个设置权限的方法，但是调用时只会抛出异常！，也就是不允许通过 set 方法设置权限！
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        super.setAuthenticated(false);
//    }
}
