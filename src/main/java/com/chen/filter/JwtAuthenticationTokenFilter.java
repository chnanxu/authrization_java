package com.chen.filter;

import cn.hutool.core.util.StrUtil;
import com.chen.utils.result.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取JWT
        String token=request.getHeader("token");
        logger.info("token-------->"+token);

        if(StrUtil.isBlankOrUndefined(token)){
            filterChain.doFilter(request,response);
            return;
        }

        if(token!=null){
            JwtUtils.tokenVerify(token);


        }
        filterChain.doFilter(request,response);
    }
}
