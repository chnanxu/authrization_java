package com.chen.filter;

import cn.hutool.json.JSONUtil;
import com.chen.pojo.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported:"+request.getMethod());
        }

        User user=obtainUser(request);

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

        return this.getAuthenticationManager().authenticate(authenticationToken);

    }


    private User obtainUser(HttpServletRequest request) throws IOException {
        BufferedReader reader= request.getReader();
        StringBuffer sbf=new StringBuffer();
        String line=null;
        while((line=reader.readLine())!=null){
            sbf.append(line);
        }

        User user=JSONUtil.parseObj(sbf.toString()).toBean(User.class);
        return user;
    }
}
