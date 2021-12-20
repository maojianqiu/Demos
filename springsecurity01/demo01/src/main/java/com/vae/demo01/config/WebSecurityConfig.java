package com.vae.demo01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import sun.security.util.Password;

import javax.sql.DataSource;

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
    //-------------------------------------------------------------------------------------------------------
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

    //-------------------------------------------------------------------------------------------------------
    // 4. 的代码
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                //设置自定义登录页面和请求不设置访问权限
                .antMatchers("/newlogin.html").permitAll()
                //设置游客、admin、user访问的权限，.hasRole(Str) 方法是给匹配的路径设置角色，当用户有这个角色时就可以访问这个路径。
                .antMatchers("/autho/all/**").permitAll()
                .antMatchers("/autho/admin/**").hasRole("ADMIN")
                .antMatchers("/autho/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //设置自定义的登录页面
                .loginPage("/newlogin.html")
                .and()
                .csrf().disable();
    }

//    基于默认内存模型的用户模式
//    @Bean
//    public UserDetailsService userDetailsService(){
//        //实现了 UserDetailsService 接口，覆写了 loadUserByUsername()方法， 并添加了创建用户的方法
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        //创建用户，User 类继承了 UserDetails 接口，并提供了创建方法
//        manager.createUser(User.withUsername("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN").build());
//        manager.createUser(User.withUsername("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER").build());
//        return manager;
//    }

    @Bean
    public BCryptPasswordEncoder password(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

//    基于默认数据库模型的用户模式
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        if(!manager.userExists("admin")){
            //下面的创建用户就相当于通过 jdbc 调用 mysql 的 insert
            manager.createUser(User.withUsername("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN").build());
        }

       return  manager;
    }
}
