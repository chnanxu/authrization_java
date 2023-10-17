package com.chen.controller;


import com.auth0.jwt.JWT;
import com.chen.mapper.UserMapper;
import com.chen.pojo.User;
import com.chen.pojo.user.UserInfo;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.RedisCache;
import com.chen.utils.result.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private final RedisCache redisCache;

    @Autowired
    public UserController(RedisCache redisCache){
        this.redisCache=redisCache;
    }

    @PreAuthorize("hasAuthority('system:user')")
    @RequestMapping("/home")
    public ResponseResult<String> userTest(@RequestHeader String token){

        User user=redisCache.getCacheObject("user:"+token);
        UserInfo userInfo=userMapper.findUserInfo(user.getUid());
        return new ResponseResult(CommonCode.SUCCESS,userInfo);
    }
}
