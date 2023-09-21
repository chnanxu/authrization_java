package com.chen.exception;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.chen.utils.result.Code;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.JwtUtils;
import com.chen.utils.result.ResponseResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json,charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println(authentication);
        String token = JwtUtils.token(authentication);
        response.addHeader("token",token);
        Map<String,Object> tokenInfo=new HashMap<>();
        tokenInfo.put("token",token);
        ResponseResult result=new ResponseResult(CommonCode.SUCCESS,tokenInfo);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
