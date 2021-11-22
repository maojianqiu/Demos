package com.vae.configuration.spring.security;

import com.vae.configuration.properties.SystemConfig;
import com.vae.domain.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;


/**
 * 备注：
 * 像security过滤链这种的，即使没有通过 .addFilterAt  .addFilterBefore  .addFilterAfter 进行加入，只要继承了 security 链中的类/接口并且生成了bean(@Compoent 、@Bean)就会被 security 过滤链找到并加入，
 * 所以如果不想要使用某一个自定义过滤器，就不要生成对应的 bean 对象。
 *
 * configure(HttpSecurity http) 中，添加的过滤路径 要记住是由先后判断顺序的。
 *
 * @author 武汉思维跳跃科技有限公司
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final SystemConfig systemConfig;
        private final LoginAuthenticationEntryPoint restAuthenticationEntryPoint;
        private final RestLogoutSuccessHandler restLogoutSuccessHandler;
        private final RestAccessDeniedHandler restAccessDeniedHandler;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(SystemConfig systemConfig, LoginAuthenticationEntryPoint restAuthenticationEntryPoint,
                                                      RestLogoutSuccessHandler restLogoutSuccessHandler, RestAccessDeniedHandler restAccessDeniedHandler) {
            this.systemConfig = systemConfig;
            this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
            this.restLogoutSuccessHandler = restLogoutSuccessHandler;
            this.restAccessDeniedHandler = restAccessDeniedHandler;
        }

        /**
         * 1.过滤身份认证，
         * 2.添加白名单和其余路径匹配
         * 3.处理权限不足
         * 4.处理登录成功后、失败后
         * 5.处理未登录
         * 6.添加登陆路径过滤
         * 7.处理登出成功 以及  登出后 session 失效
         * 8.设置跨站请求伪造 无效
         * 9.设置跨域请求
         *
         * ~~ 1.相当于每次“登录”时都在 自定义的身份认证过滤链（RestLoginAuthenticationFilter）中进行认证，认证失败则失败，认证成功则拿到用户的用户名密码和权限（ADMIN STUDENT etc），
         * ~~ 然后在最后 FilterSecurityInterpreter 中进行路劲匹配和权限匹配。
         * ~~ 2.当前代码，没有使用 security 自带的 UserDtasil ,而是在自定义的 AuthenticationProvider 中使用自定义的 UserService
         * ~~ 所以其实是后端这边定死了 什么角色有什么接口权限，不能任意分配。
         *
         * @param http http
         * @throws Exception exception
         *                   csrf is the from submit get method
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.headers().frameOptions().disable();

            List<String> securityIgnoreUrls = systemConfig.getSecurityIgnoreUrls();
            String[] ignores = new String[securityIgnoreUrls.size()];
            http
                    .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(securityIgnoreUrls.toArray(ignores)).permitAll()
                    .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                    .antMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
                    .anyRequest().permitAll()
                    .and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                    .and()
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    //添加认证管理器
                    .and().logout().logoutUrl("/api/user/logout").logoutSuccessHandler(restLogoutSuccessHandler).invalidateHttpSession(true)
                    .and().csrf().disable()
                    .cors();
        }


        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setMaxAge(3600L);
            configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/api/**", configuration);
            return source;
        }


        @Bean
        public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
            return new JwtAuthenticationTokenFilter();
        }


    }
}
