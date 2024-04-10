package com.chen.exception;

import cn.hutool.json.JSONUtil;
import com.chen.utils.result.ResponseResult;
import com.chen.utils.result.UserCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json,charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ResponseResult result=new ResponseResult(UserCode.NOLOGIN,authException);
        PrintWriter writer=response.getWriter();
        writer.write(JSONUtil.toJsonStr(result));
    }
}
