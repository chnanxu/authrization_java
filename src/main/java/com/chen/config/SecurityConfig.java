package com.chen.config;


//import com.chen.filter.JwtAuthenticationTokenFilter;

//import com.chen.filter.LoginFilter;


//开启SpringSecurity  默认会注册大量的过滤器 servlet filter
//过滤器链【责任链模式】
//@Configuration
//@EnableWebSecurity//标识为spring security的配置类
//@RequiredArgsConstructor
//@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)  //开启注解权限控制
//public class SecurityConfig{

    /**
     * JWT Token 过滤器链
     * @param http
     * @return
     * @throws Exception
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        //authorizeHttpRequests:针对http请求进行授权配置
//        //login登录页面需要匿名访问
//        //permitAll：具有所有权限 也就是可以匿名访问
//        //anyRequest：任何请求 所有请求
//        //authenticated：认证
////        http.authorizeHttpRequests(authorizeRequests->
////                authorizeRequests
////
////                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
////                        .requestMatchers( "/","/index","/*","/reg","/login","/logout", "/*.html", "/*/*.html", "/*/*.css", "/*/*.js", "/profile/**").permitAll()
////
////                        .requestMatchers("/user/**").hasAnyAuthority("system:user","system:admin","system:root")
////
////                        .requestMatchers("/admin/**").hasAnyAuthority("system:admin","system:root")
////
////                        .requestMatchers("/root/**").hasAnyAuthority("system:root")
////
////                        .requestMatchers("/captcha/**").permitAll()
////
////        );
//
////
////        http.formLogin((formlogin)->formlogin
////                .permitAll()
////                .successHandler(new LoginSuccessHandler())
////                .failureHandler(new LoginFailureHandler())
////        );
//
//        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        //配置自定义登录过滤器
//        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        //登录之前获取token并且校验
//        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//
//        http.authenticationProvider(authenticationProvider());
//
//        //添加异常处理器 JWT token
//        http.exceptionHandling(e->e
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .accessDeniedHandler(accessDeniedHandler)
//        );
//
//
//        //退出功能
//        http.logout(logout->logout.invalidateHttpSession(true).logoutSuccessHandler(new LogoutSuccessHandlerImpl(redisCache())));
//
//
//        ;
//
//        //Customizer.withDefaults():关闭
//        //http.csrf(Customizer.withDefaults());//跨域漏洞防御:关闭
//        //http.csrf(e->e.disable());
//        //http.csrf(csrf->csrf.disable());//相当于http.csrf(Customizer.withDefaults());
//        http.csrf().disable();
//        http.httpBasic().disable();
//
//
//        return http.build();
//
//    }

    /**
     * 默认过滤器链
     */

//    @Bean
//    public CorsFilter corsFilter(){
//
//        CorsConfiguration configuration=new CorsConfiguration();
//
//        configuration.addAllowedOrigin("http://127.0.0.1:3000");
//        //设置跨域访问可以携带cookie
//        configuration.setAllowCredentials(true);
//        //允许所有的请求方法==》 GET POST PUT DELETE
//        configuration.addAllowedMethod("*");
//
//        configuration.addAllowedHeader("*");
//
//
//        UrlBasedCorsConfigurationSource configurationSource=new UrlBasedCorsConfigurationSource();
//
//        configurationSource.registerCorsConfiguration("/**",configuration);
//
//
//        return new CorsFilter();
//
//    }


    /**
     * Spring Authorization Server过滤器链
     */

//
//
//    @Bean
//    @SneakyThrows
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
//        return authenticationConfiguration.getAuthenticationManager();
//    }






//
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        // 提供自定义loadUserByUsername
//        authProvider.setUserDetailsService(userDetailService);
//        // 指定密码编辑器
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//
//    @Autowired
//    private AuthenticationConfiguration authenticationConfiguration;
//
//    //自定义登录过滤器
//    public LoginFilter loginFilter() throws Exception{
//        LoginFilter loginFilter=new LoginFilter();
//        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(redisCache()));
//        loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
//
//
//        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
//        loginFilter.setFilterProcessesUrl("/login");
//
//        return loginFilter;
//    }
//
//
//    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
//        JwtAuthenticationTokenFilter jwtFilter=new JwtAuthenticationTokenFilter();
//        return jwtFilter;
//    }





//    //注入Redis
//    @Bean
//    public RedisCache redisCache(){
//        return new RedisCache();
//    }
//
//
//    /**
//     * 明文加密
//     * PasswordEncoder ：加密编码
//     * 开发环境一般是明文加密，生产环境是密文加密
//     * */





//}
