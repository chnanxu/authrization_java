package com.chen.exception;

import cn.hutool.json.JSONUtil;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static cn.hutool.http.ContentType.JSON;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json,charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ResponseResult result=new ResponseResult(CommonCode.SUCCESS,authentication);
        PrintWriter writer=response.getWriter();
        writer.write(JSONUtil.toJsonStr(result));
    }
}
