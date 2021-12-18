package com.vae.demo01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
    // 3. 的代码
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                //2.设置自定义登录页面和请求不设置访问权限
//                .antMatchers("/newlogin.html").permitAll()
//                .antMatchers("/loginHandle").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                //1.设置自定义的登录页面
//                .loginPage("/newlogin.html")
//                .loginProcessingUrl("/loginHandle")
//                .and()
//                .csrf().disable();
//
//    }


    // 4. 的代码
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                //2.设置自定义登录页面和请求不设置访问权限
                .antMatchers("/newlogin.html").permitAll()
                .antMatchers("/autho/admin/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //1.设置自定义的登录页面
                .loginPage("/newlogin.html")
                .and()
                .csrf().disable();

    }
}
