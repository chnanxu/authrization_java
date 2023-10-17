package com.chen.exception;

import com.alibaba.fastjson.JSON;
import com.chen.pojo.LoginUser;
import com.chen.pojo.User;
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

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final RedisCache redisCache;
    @Autowired
    public LoginSuccessHandler(RedisCache redisCache){
        this.redisCache=redisCache;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json,charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //生成token
        String token = JwtUtils.token(authentication);
        //使用token将用户信息存入到redis中
        LoginUser loginUser=(LoginUser)authentication.getPrincipal();
        User user=loginUser.getUser();
        redisCache.setCacheObject("user:"+token,user);

        response.addHeader("token",token);
        Map<String,Object> tokenInfo=new HashMap<>();
        tokenInfo.put("token",token);
        ResponseResult result=new ResponseResult(UserCode.LOGINSUCCESS,tokenInfo);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
