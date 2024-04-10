//package com.chen.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.auth0.jwt.JWT;
//import com.chen.utils.result.JwtAuthentication;
//import com.chen.utils.result.JwtUtils;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        //获取JWT
//        String token=request.getHeader("token");
//        if(token == null || token.equals("")){
//            filterChain.doFilter(request,response);
//            return;
//        }
//
//        try{
//            //再次发起请求时验证token
//            JwtUtils.tokenVerify(token);
//            //验证通过后获取权限信息
//            String audience = JWT.decode(token).getAudience().get(0);
//            JwtAuthentication jwtAuthentication= JSON.parseObject(audience, JwtAuthentication.class);
//            Object principal = jwtAuthentication.getPrincipal();
//
//            //对当前用户进行授权
//            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(principal,null,jwtAuthentication.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//            throw new RuntimeException("token异常");
//        }
//
//
//        filterChain.doFilter(request,response);
//
//    }
//}
