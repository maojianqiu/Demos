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
 * @author 武汉思维跳跃科技有限公司
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final SystemConfig systemConfig;
        private final LoginAuthenticationEntryPoint restAuthenticationEntryPoint;
        private final RestAuthenticationProvider restAuthenticationProvider;
//        private final RestDetailsServiceImpl formDetailsService;
        private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
        private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
        private final RestLogoutSuccessHandler restLogoutSuccessHandler;
        private final RestAccessDeniedHandler restAccessDeniedHandler;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(SystemConfig systemConfig, LoginAuthenticationEntryPoint restAuthenticationEntryPoint,
                                                     RestAuthenticationProvider restAuthenticationProvider,
//                                                     RestDetailsServiceImpl formDetailsService,
                                                     RestAuthenticationSuccessHandler restAuthenticationSuccessHandler, RestAuthenticationFailureHandler restAuthenticationFailureHandler, RestLogoutSuccessHandler restLogoutSuccessHandler, RestAccessDeniedHandler restAccessDeniedHandler) {
            this.systemConfig = systemConfig;
            this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
            this.restAuthenticationProvider = restAuthenticationProvider;
//            this.formDetailsService = formDetailsService;
            this.restAuthenticationSuccessHandler = restAuthenticationSuccessHandler;
            this.restAuthenticationFailureHandler = restAuthenticationFailureHandler;
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
                    .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authenticationProvider(restAuthenticationProvider)
                    .authorizeRequests()
                    .antMatchers(securityIgnoreUrls.toArray(ignores)).permitAll()
                    .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                    .antMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
                    .anyRequest().permitAll()
                    .and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                    .and().formLogin().successHandler(restAuthenticationSuccessHandler).failureHandler(restAuthenticationFailureHandler)
                    .and()
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    //添加认证管理器
                    .and().logout().logoutUrl("/api/user/logout").logoutSuccessHandler(restLogoutSuccessHandler).invalidateHttpSession(true)
//                    .and().rememberMe().key(CookieConfig.getName()).tokenValiditySeconds(CookieConfig.getInterval()).userDetailsService(formDetailsService)
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
        public RestLoginAuthenticationFilter authenticationFilter() throws Exception {
            RestLoginAuthenticationFilter authenticationFilter = new RestLoginAuthenticationFilter();
            authenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
            authenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
            authenticationFilter.setAuthenticationManager(authenticationManagerBean());
            //方法是自己写的
//            authenticationFilter.setUserDetailsService(formDetailsService);
            return authenticationFilter;
        }


    }
}
