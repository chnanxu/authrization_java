//package com.chen.filter;
//
//import cn.hutool.json.JSONUtil;
//import com.alibaba.fastjson.JSON;
//import com.chen.exception.handler.LoginFailureHandler;
//import com.chen.pojo.User;
//
//import com.chen.utils.result.CommonCode;
//import com.chen.utils.result.ResponseResult;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.SneakyThrows;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Objects;
//
//public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    @SneakyThrows
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        if(!request.getMethod().equals("POST")){
//            throw new AuthenticationServiceException("Authentication method not supported:"+request.getMethod());
//        }
//
//        User user=obtainUser(request);
//
//        String sysCheck= (String) request.getSession().getAttribute("captcha");
//        String userCheck=user.getCaptcha();
//        if(!Objects.equals(sysCheck, userCheck)){
//            throw new BadCredentialsException("验证码错误");
//        }
//
//
//        try{
//            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
//
//            return this.getAuthenticationManager().authenticate(authenticationToken);
//        }catch (InternalAuthenticationServiceException internalAuthenticationServiceException){
//            throw new BadCredentialsException("账号不存在");
//        }catch (BadCredentialsException badCredentialsException){
//            throw new BadCredentialsException("密码错误");
//        }
//
//
//    }
//
//
//    private User obtainUser(HttpServletRequest request) throws IOException {
//        BufferedReader reader= request.getReader();
//        StringBuffer sbf=new StringBuffer();
//        String line=null;
//        while((line=reader.readLine())!=null){
//            sbf.append(line);
//        }
//
//        return JSONUtil.parseObj(sbf.toString()).toBean(User.class);
//    }
//}
