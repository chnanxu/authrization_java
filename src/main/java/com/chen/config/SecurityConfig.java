package com.chen.config;


import com.chen.exception.LoginFailureHandler;
import com.chen.exception.LoginSuccessHandler;
import com.chen.filter.LoginFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

//开启SpringSecurity  默认会注册大量的过滤器 servlet filter
//过滤器链【责任链模式】
@Configuration
@EnableWebSecurity //标识为spring security的配置类
public class SecurityConfig{

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //authorizeHttpRequests:针对http请求进行授权配置
        //login登录页面需要匿名访问
        //permitAll：具有所有权限 也就是可以匿名访问
        //anyRequest：任何请求 所有请求
        //authenticated：认证
        http.authorizeHttpRequests(authorizeHttpRequests->
                authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/", "/*.html", "/*/*.html", "/*/*.css", "/*/*.js", "/profile/**").permitAll()

                        .requestMatchers("/captcha/**").permitAll()

                        .requestMatchers("/login","/","/index","/reg").permitAll()

                        .requestMatchers("/user/**").hasAnyAuthority("user","admin","root")


                        .requestMatchers("/admin/**").hasAnyAuthority("admin","root")


                        .requestMatchers("/root/**").hasAuthority("root")



        );//http后面可以一直.内容   太多反而不美观


        //配置自定义登录过滤器
        //将UsernamePasswordAuthenticationFilter替换掉
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器

        http.exceptionHandling(e->e.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint));

        //loginPage:登录页面
        //loginProcessingUrl:登录接口过滤器


        //退出功能
        http.logout(logout->logout.invalidateHttpSession(true));


        ;

        //Customizer.withDefaults():关闭
        //http.csrf(Customizer.withDefaults());//跨域漏洞防御:关闭
        //http.csrf(e->e.disable());
        //http.csrf(csrf->csrf.disable());//相当于http.csrf(Customizer.withDefaults());
        http.csrf(e->e.disable());
        http.cors(withDefaults());


        return http.build();

    }

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter=new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return loginFilter;
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
