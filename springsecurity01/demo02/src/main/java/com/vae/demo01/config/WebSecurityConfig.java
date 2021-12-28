package com.vae.demo01.config;

import com.vae.demo01.base.PhoneAuthenticationProvider;
import com.vae.demo01.filter.PhonePasswordFilter;
import com.vae.demo01.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.security.util.Password;

import javax.sql.DataSource;
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
                //addFilter 就是添加过滤器的，我们直接添加到默认的 UsernamePasswordAuthenticationFilter 前面。
                .addFilterBefore(getPhonePasswordFilter(),UsernamePasswordAuthenticationFilter.class);

                //这里添加自定义的 Povider，是直接加到第一个调用的 AuthenticationManager#providers里面，并且 parent 里面的 providers 也是这个；
                //.authenticationProvider(getPhoneAuthenticationProvider()).userDetailsService(getUserDetailsService());

        http.authorizeRequests()

                .antMatchers("/newlogin.html").permitAll()
                //设置自定义手机号码登录请求不设置访问权限
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
    public PhonePasswordFilter getPhonePasswordFilter() throws Exception {
        PhonePasswordFilter phonePasswordFilter = new PhonePasswordFilter("/phoneUrl");
        //给 providerManager 添加 provider,这里是添加到最后一个 AuthenticationManager#providers 里面，也就是会通过 parent 调用。
        ProviderManager providerManager =
                new ProviderManager(Collections.singletonList(getPhoneAuthenticationProvider()));
        //这里需要给自定义的Filter注入 AuthenticationManager，是调用 WebSecurityConfigurerAdapter 的方法！
        phonePasswordFilter.setAuthenticationManager(providerManager);
        return phonePasswordFilter;
    }

    @Bean
    public PhoneAuthenticationProvider getPhoneAuthenticationProvider(){
        return new PhoneAuthenticationProvider();
    }
}
