package com.vae.configuration.spring.security;

import com.vae.configuration.properties.SystemConfig;
import com.vae.configuration.spring.security.authentication.RestAuthenticationProvider;
import com.vae.configuration.spring.security.authentication.RestLoginAuthenticationFilter;
import com.vae.configuration.spring.security.authorization.RestSecurityService;
import com.vae.configuration.spring.security.authorization.RestAuthorizationFilter;
import com.vae.configuration.spring.security.handle.*;
import com.vae.domain.enums.RoleEnum;
import com.vae.domain.model.UmsResource;
import com.vae.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private  SystemConfig systemConfig;
        private  LoginAuthenticationEntryPoint restAuthenticationEntryPoint;
        private  RestAuthenticationProvider restAuthenticationProvider;
        private  RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
        private  RestAuthenticationFailureHandler restAuthenticationFailureHandler;
        private  RestLogoutSuccessHandler restLogoutSuccessHandler;
        private  RestAccessDeniedHandler restAccessDeniedHandler;
        private  RestSecurityService restSecurityService;

        @Autowired
        public SecurityConfig(SystemConfig systemConfig,
                                                     LoginAuthenticationEntryPoint restAuthenticationEntryPoint,
                                                     RestAuthenticationProvider restAuthenticationProvider,
                                                     RestAuthenticationSuccessHandler restAuthenticationSuccessHandler,
                                                     RestAuthenticationFailureHandler restAuthenticationFailureHandler,
                                                     RestLogoutSuccessHandler restLogoutSuccessHandler,
                                                     RestAccessDeniedHandler restAccessDeniedHandler,
                                                     RestSecurityService restSecurityService
        ) {
            this.systemConfig = systemConfig;
            this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
            this.restAuthenticationProvider = restAuthenticationProvider;
            this.restAuthenticationSuccessHandler = restAuthenticationSuccessHandler;
            this.restAuthenticationFailureHandler = restAuthenticationFailureHandler;
            this.restLogoutSuccessHandler = restLogoutSuccessHandler;
            this.restAccessDeniedHandler = restAccessDeniedHandler;
            this.restSecurityService = restSecurityService;
        }

    public SecurityConfig() {
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
         * 10.添加自定义的动态权限过滤器，在最终的过滤器之前
         *
         * ~~ 1.相当于每次“登录”时都在 自定义的身份认证过滤链（RestLoginAuthenticationFilter）中进行认证，认证失败则失败，认证成功则拿到用户的用户名密码和权限（ADMIN STUDENT etc），
         * ~~ 然后在最后 FilterSecurityInterpreter 中进行路劲匹配和权限匹配。
         * ~~ 2.当前代码，没有使用 security 自带的 UserDtasil ,而是在自定义的 AuthenticationProvider 中使用自定义的 UserService
         * ~~ 所以其实是后端这边定死了 什么角色有什么接口权限，不能任意分配。
         * ~~ 3.注意，其实不用 .and().formLogin().successHandler 添加处理器也可以，只要有对应的 bean 就会搜索到，并且注入使用。但为了清楚都用了什么，还是添使用比较好。
         *
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
//                    .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
//                    .antMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
//                    .anyRequest().permitAll()
                    .and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                    .and().formLogin().successHandler(restAuthenticationSuccessHandler).failureHandler(restAuthenticationFailureHandler)
                    .and()
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    //添加认证管理器
                    .and().logout().logoutUrl("/api/user/logout").logoutSuccessHandler(restLogoutSuccessHandler).invalidateHttpSession(true)
//                    .and().rememberMe().key(CookieConfig.getName()).tokenValiditySeconds(CookieConfig.getInterval()).userDetailsService(formDetailsService)
                    .and().csrf().disable()
                    .cors();

            //有动态权限配置时添加动态权限校验过滤器
            if (this.restSecurityService != null) {
                http.authorizeRequests().and().addFilterBefore(restAuthorizationFilter(), FilterSecurityInterceptor.class);
            }
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

        @Bean
        public RestAuthorizationFilter restAuthorizationFilter() {
            return new RestAuthorizationFilter();
        }



/*
* 注意：这里如果使用匿名内部类产生 bean ，就不能在当前配置类中 引入（Autowried）这个 bean ，会产生循环依赖
* */
//        @Bean
//        public RestSecurityService restSecurityService() {
//            return new RestSecurityService() {
//                @Override
//                public Map<String, ConfigAttribute> loadDataSource() {
//                    System.out.println("----------------");
//        //                    Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
//        //                    List<UmsResource> resourceList = umsResourceService.listAll();
//        //                    for (UmsResource resource : resourceList) {
//        //                        map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
//        //                    }
//        //                    return map;
//                    return null;
//                }
//            };
//        }


}
