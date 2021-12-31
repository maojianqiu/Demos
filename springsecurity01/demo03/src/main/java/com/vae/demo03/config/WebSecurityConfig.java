package com.vae.demo03.config;

import com.vae.demo03.base.PhoneAuthenticationProvider;
import com.vae.demo03.filter.PhonePasswordFilter;
import com.vae.demo03.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

/*
* 如果你使用的依赖包是依赖于 spring-boot-starter-parent 的，就只用 @Configuration 注解即可。
* 如果你使用的是单独的 spring-security-config、web、core（三个），就需要使用 @EnableWebSecurity注解。
*
* @EnableWebSecurity 注解会注入 WebSecurityConfigurerAdapter 类中的相关配置。
* 因为 spring-boot-starter-security 依赖包中引入了  spring-boot-starter ，这个包 是需要 引入 spring-boot-autoconfigure 这个 包的，关键就在这里 ，autoconfigure 是通过SPI自动加载注册一些需要的类 .
* 这里默认已经 自动注入了 Security 的 自动配置类 SecurityAutoConfiguration.class
* */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .authenticationProvider(getPhoneAuthenticationProvider())
                .userDetailsService(getUserDetailsService())
                .addFilterBefore(getPhonePasswordFilter(),UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()

                .antMatchers("/newlogin.html").permitAll()
                .antMatchers("/phoneUrl").permitAll()
                .antMatchers("/autho/all/**").permitAll()
                .antMatchers("/autho/admin/**").hasRole("ADMIN")
                .antMatchers("/autho/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/newlogin.html")
                .and()
                .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public MyUserDetailsService getUserDetailsService(){
        return new MyUserDetailsService();
    }

    @Bean
    public PhoneAuthenticationProvider getPhoneAuthenticationProvider(){
        PhoneAuthenticationProvider phoneAuthenticationProvider = new PhoneAuthenticationProvider();
        return new PhoneAuthenticationProvider();
    }

    @Bean
    public PhonePasswordFilter getPhonePasswordFilter() throws Exception {
        PhonePasswordFilter phonePasswordFilter = new PhonePasswordFilter("/phoneUrl");
        phonePasswordFilter.setAuthenticationManager(authenticationManager());
        return phonePasswordFilter;
    }


}
