package com.chen.config;


import com.chen.exception.*;
import com.chen.filter.JwtAuthenticationTokenFilter;

import com.chen.filter.LoginFilter;
import com.chen.service.UserDetailServiceImpl;

import com.chen.utils.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

//开启SpringSecurity  默认会注册大量的过滤器 servlet filter
//过滤器链【责任链模式】
@Configuration
@EnableWebSecurity//标识为spring security的配置类
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)  //开启注解权限控制
public class SecurityConfig{

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //authorizeHttpRequests:针对http请求进行授权配置
        //login登录页面需要匿名访问
        //permitAll：具有所有权限 也就是可以匿名访问
        //anyRequest：任何请求 所有请求
        //authenticated：认证
//        http.authorizeHttpRequests(authorizeRequests->
//                authorizeRequests
//
//                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
//                        .requestMatchers( "/","/index","/*","/reg","/login","/logout", "/*.html", "/*/*.html", "/*/*.css", "/*/*.js", "/profile/**").permitAll()
//
//                        .requestMatchers("/user/**").hasAnyAuthority("system:user","system:admin","system:root")
//
//                        .requestMatchers("/admin/**").hasAnyAuthority("system:admin","system:root")
//
//                        .requestMatchers("/root/**").hasAnyAuthority("system:root")
//
//                        .requestMatchers("/captcha/**").permitAll()
//
//        );

//
//        http.formLogin((formlogin)->formlogin
//                .permitAll()
//                .successHandler(new LoginSuccessHandler())
//                .failureHandler(new LoginFailureHandler())
//        );

        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //配置自定义登录过滤器
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        //登录之前获取token并且校验
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(authenticationProvider());

        //添加异常处理器
        http.exceptionHandling(e->e
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );



        //退出功能
        http.logout(logout->logout.invalidateHttpSession(true).logoutSuccessHandler(logoutSuccessHandler));


        ;

        //Customizer.withDefaults():关闭
        //http.csrf(Customizer.withDefaults());//跨域漏洞防御:关闭
        //http.csrf(e->e.disable());
        //http.csrf(csrf->csrf.disable());//相当于http.csrf(Customizer.withDefaults());
        http.csrf().disable();
        http.httpBasic().disable();


        return http.build();

    }





    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 提供自定义loadUserByUsername
        authProvider.setUserDetailsService(userDetailService);
        // 指定密码编辑器
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;


    public LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter=new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(redisCache()));
        loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());

        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        loginFilter.setFilterProcessesUrl("/login");
        loginFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        return loginFilter;
    }


    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        JwtAuthenticationTokenFilter jwtFilter=new JwtAuthenticationTokenFilter();
        return jwtFilter;
    }




    @Bean
    public RedisCache redisCache(){
        return new RedisCache();
    }


    /**
     * 明文加密
     * PasswordEncoder ：加密编码
     * 开发环境一般是明文加密，生产环境是密文加密
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }





}
