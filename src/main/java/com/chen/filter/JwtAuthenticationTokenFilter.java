package com.chen.filter;


import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.chen.pojo.LoginUser;
import com.chen.pojo.User;
import com.chen.utils.result.JwtAuthentication;
import com.chen.utils.result.JwtUtils;
import com.chen.utils.result.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取JWT
        String token=request.getHeader("token");
        if(token == null || token.equals("")){
            filterChain.doFilter(request,response);
            return;
        }

        try{
            //再次发起请求时验证token
            JwtUtils.tokenVerify(token);
            //验证通过后获取权限信息
            String audience = JWT.decode(token).getAudience().get(0);
            JwtAuthentication jwtAuthentication= JSON.parseObject(audience, JwtAuthentication.class);
            Object principal = jwtAuthentication.getPrincipal();

            //对当前用户进行授权
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(principal,null,jwtAuthentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }catch(Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("token异常");
        }


        filterChain.doFilter(request,response);

    }
}
