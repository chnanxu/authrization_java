package com.chen.exception;

import com.alibaba.fastjson.JSON;
import com.chen.pojo.LoginUser;
import com.chen.utils.result.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json,charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String token = JwtUtils.token(authentication);

        response.addHeader("token",token);
        Map<String,Object> tokenInfo=new HashMap<>();
        tokenInfo.put("token",token);
        ResponseResult result=new ResponseResult(UserCode.LOGINSUCCESS,tokenInfo);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
