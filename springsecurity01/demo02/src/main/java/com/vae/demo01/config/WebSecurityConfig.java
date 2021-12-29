package com.vae.demo01.config;

import com.vae.demo01.base.PhoneAuthenticationProvider;
import com.vae.demo01.filter.PhonePasswordFilter;
import com.vae.demo01.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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

    /**
     * 当我们注入 provider 时，有很多种方式：
     * 方式 1.直接在 WebSecurityConfigurerAdapter # configure(HttpSecurity http) 中 调用 http.authenticationProvider(provider) 进行添加，
     * 方式 2.在 PhonePasswordFilter bean 里面 new providerManager(Provider)，进行添加
     * 方式 3.在 PhonePasswordFilter bean 里面调用 WebSecurityConfigurerAdapter # authenticationManager() 进行添加
     *
     * 但是切记，调用生成 manager 时，只会自动注入自定义的 provider ，并不会主动注入 UserDetailsService ，必须主动注入！
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
        /**
         * 方式 3：这里添加自定义的 Povider，是会将 provider 直接添加到 security 默认的 manager 里面，也就是 WebSecurityConfigurerAdapter 创建的 AuthenticationManager 里面的 providers 里面！
         *      也就是 security 默认的 AuthenticationManager ，如 UsernamePasswordAuthenticationFilter 里面的 AuthenticationManager 对象里面的 provider 是会有这里添加的 PhoneAuthenticationProvider ，并且 parent 里面的 provider 也会有 PhoneAuthenticationProvider（毕竟 Adapter 是会搜到自定义 Provider 并自动注入的），并且 parent 就是这里创建的 AuthenticationManager ！
         */
//                .authenticationProvider(getPhoneAuthenticationProvider())
                //注意：添加自定义provider 之后，security 配置时就不会注入 DaoAuthenticationProvider 了，如果还想使用，就调用下方代码；
                .userDetailsService(getUserDetailsService())

            //addFilter 就是添加过滤器的，我们直接添加到默认的 UsernamePasswordAuthenticationFilter 前面。
                .addFilterBefore(getPhonePasswordFilter(),UsernamePasswordAuthenticationFilter.class);

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
    public PhoneAuthenticationProvider getPhoneAuthenticationProvider(){
        PhoneAuthenticationProvider phoneAuthenticationProvider = new PhoneAuthenticationProvider();
        return new PhoneAuthenticationProvider();
    }

    @Bean
    public PhonePasswordFilter getPhonePasswordFilter() throws Exception {
        PhonePasswordFilter phonePasswordFilter = new PhonePasswordFilter("/phoneUrl");

        /**
         * 这里必须给自定义的Filter注入 AuthenticationManager，有很多种方式：
         * 方式 1.在 PhonePasswordFilter bean 里面 new providerManager(Provider)，进行添加
         * 方式 2.在 PhonePasswordFilter bean 里面调用 WebSecurityConfigurerAdapter # authenticationManager() 进行添加
         *
         * 在上面两种方式中，可以决定怎样注入对应的 provider
         */

        /**
         * 方式 2：这会创建一个新的 AuthenticationManager 给 PhonePasswordFilter，那么当我们使用 PhonePasswordFilter 的 AuthenticationManager 时，它里面的 provider 只有我们同构构建方法加进去的，并且没有 parent！同时 security 也会创建一个默认的 AuthenticationManager，这两个manager之间是没有关系的！
         * 也就是自定义的 PhonePasswordFilter 里面的 AuthenticationManager 对象里面的 provider 只有注入的 PhoneAuthenticationProvider，并且没有 parent!，
         * 而 security 默认的 AuthenticationManager ，如 UsernamePasswordAuthenticationFilter 里面的 AuthenticationManager 对象里面的 provider 还是只有默认的 Provider ，并且 parent 里面的 provider 是有 PhoneAuthenticationProvider（是通过 Adapter 自动注入的），但是 parent 与这里创建的 AuthenticationManager 是两个对象！
         */
//        ProviderManager providerManager = new ProviderManager(Collections.singletonList(getPhoneAuthenticationProvider()));
//        phonePasswordFilter.setAuthenticationManager(providerManager);

        /**
         * 方式 3：这会通过 WebSecurityConfigurerAdapter 创建一个 AuthenticationManager ，在这儿之后的 WebSecurityConfigurerAdapter # getHttp() 方法也会创建一个 security 默认的 AuthenticationManager，并且会将我们这里创建的 AuthenticationManager 作为 parent 加入到默认的 AuthenticationManager里面。这两个manager之间是有关系的！
         *      也就是自定义的 PhonePasswordFilter 里面的 AuthenticationManager 对象里面的 provider 只有注入的 PhoneAuthenticationProvider，并且没有 parent!，
         *      而 security 默认的 AuthenticationManager ，如 UsernamePasswordAuthenticationFilter 里面的 AuthenticationManager 对象里面的 provider 还是只有默认的 Provider ，但是 parent 里面的 provider 是有 PhoneAuthenticationProvider，并且 parent 就是这里创建的 AuthenticationManager ！
         */
        //我们直接在这里添加，使用 authenticationManager()，这样就不用创建多个 provider 对象了，并且也不会影响默认的 UsernamePasswordAuthenticationFilter 的 manager
        phonePasswordFilter.setAuthenticationManager(authenticationManager());

        return phonePasswordFilter;
    }




}
