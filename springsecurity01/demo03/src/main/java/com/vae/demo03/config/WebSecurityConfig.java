package com.vae.demo03.config;

import com.vae.demo03.base.DynamicAccessDecisionManager;
import com.vae.demo03.base.DynamicSecurityMetadataSource;
import com.vae.demo03.base.PhoneAuthenticationProvider;
import com.vae.demo03.filter.Dynamic01FilterSecurityInterceptor;
import com.vae.demo03.filter.PhonePasswordFilter;
import com.vae.demo03.service.MyUserDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .authenticationProvider(getPhoneAuthenticationProvider())
                .userDetailsService(getUserDetailsService())
                .addFilterBefore(getPhonePasswordFilter(),UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getDynamicSecurityFilter(), FilterSecurityInterceptor.class);

        http.authorizeRequests()

//                .antMatchers("/newlogin.html").permitAll()
//                .antMatchers("/phoneUrl").permitAll()
//                .antMatchers("/autho/all/**").permitAll()
//                .antMatchers("/autho/admin/**").hasRole("ADMIN")
//                .antMatchers("/autho/user/**").hasRole("USER")
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

    @Bean
    public Dynamic01FilterSecurityInterceptor getDynamicSecurityFilter() {
        return new Dynamic01FilterSecurityInterceptor();
    }

    @Bean
    public DynamicAccessDecisionManager getDynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    public DynamicSecurityMetadataSource getDynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

}
