package com.chen.config;

import com.chen.exception.handler.AccessDeniedHandlerImpl;
import com.chen.exception.handler.LoginFailureHandler;
import com.chen.exception.handler.LoginSuccessHandler;
import com.chen.utils.util.CustomSecurityProperties;
import com.chen.utils.util.RedisCache;
import com.chen.utils.util.SecurityUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity//标识为spring security的配置类
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)  //开启注解权限控制
public class ResourceConfig {

    private final CorsFilter corsFilter;

    private final CustomSecurityProperties customSecurityProperties;



    private final RedisCache redisCache;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)throws Exception{


        SecurityUtils.applyBasicSecurity(http,corsFilter,customSecurityProperties);

        http.authorizeHttpRequests((authorize)->authorize
                .requestMatchers(customSecurityProperties.getIgnoreUriList().toArray(new String[0])).permitAll()
                .anyRequest().permitAll()

        ).formLogin(formLogin->{formLogin
                      .loginPage("/login");
            if(UrlUtils.isAbsoluteUrl(customSecurityProperties.getLoginUrl())){
                formLogin.successHandler(new LoginSuccessHandler(redisCache));
                formLogin.failureHandler(new LoginFailureHandler());
            }
        }
        );

//        http.oauth2Login(oauth2Login->oauth2Login
//                .loginPage(customSecurityProperties.getLoginUrl())
//                .authorizationEndpoint(authorization->authorization
//                        .authorizationRequestResolver(this.))


        return http.build();
    }

}
