package com.vae.demo01.base;

import com.vae.demo01.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class PhoneAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    //这里需要注入bean
    @Autowired
    private PasswordEncoder passwordEncoder;

    //这里需要注入bean
    @Autowired
    private MyUserDetailsService userDetailsService;

    //记录是否查找到用户
    private volatile String userNotFoundEncodedPassword;

    //构造方法中 set 密码解析器
//    public PhoneAuthenticationProvider() {
//        this.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
//    }

    // set 引入密码解析器，判断是否存在
//    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
//        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
//        this.passwordEncoder = passwordEncoder;
//        this.userNotFoundEncodedPassword = null;
//    }

//    protected PasswordEncoder getPasswordEncoder() {
//        return this.passwordEncoder;
//    }

//    @Autowired
//    // set 引入用户详情service
//    public void setUserDetailsService(MyUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

//    protected MyUserDetailsService getUserDetailsService() {
//        return this.userDetailsService;
//    }

    //（可不添加）通过名字可以解析为：准备计时攻击防护，这个在 #retrieveUser() 方法的第一行就进行调用，
    private void prepareTimingAttackProtection() {
        //这里会将一串字符 "userNotFoundPassword" 进行编码后赋值给 this.userNotFoundEncodedPassword，
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode("userNotFoundPassword");
        }

    }

    //（可不添加）通过名字可以解析为：抵御定时攻击，这个在 #retrieveUser() 方法中拿取不到对象时，catch里面第一行进行调用，
    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
        //这里是如果没有搜索到用户，就会调用请求中的密码和 this.userNotFoundEncodedPassword 进行判等，这一定会为 false ！为什么要多此一举呢？
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }

    }

    //（可不添加）这个是提前判断有没有引入 UserDetailsService
    protected void doAfterPropertiesSet() {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }


    /**
     * （核心）判断密码是否一致，是由 父类 中的 authenticate() 进行调用
     * userDetails：数据源信息
     * authentication：请求的信息
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //若请求信息中的密码信息为 null ，则抛出异常
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            //不为 null 则进行密码判等
            String presentedPassword = authentication.getCredentials().toString();
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Failed to authenticate since password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    //（核心）这里是去数据源中拿取用户信息，是由 父类 中的 authenticate() 进行调用
    @Override
    protected UserDetails retrieveUser(String phone, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //这里调用准备计时攻击防护方法
        this.prepareTimingAttackProtection();

        try {
            //这里通过 UserDetailsService 类，拿到用户信息，若没有就是 null
            UserDetails loadedUser = this.userDetailsService.loadUserByPhone(phone);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException var4) {
            //这里调用抵御定时攻击方法
            this.mitigateAgainstTimingAttack(authentication);
            throw var4;
        } catch (InternalAuthenticationServiceException var5) {
            throw var5;
        } catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
    }

    //（核心）覆写 supports 方法，里面需要判断是否是自定义的  PhonePasswordAuthenticationToken 类
    public boolean supports(Class<?> authentication) {
        return PhonePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
