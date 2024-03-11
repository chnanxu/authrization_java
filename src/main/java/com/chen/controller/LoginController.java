package com.chen.controller;

import com.chen.pojo.User;
import com.chen.service.UserDetailServiceImpl;
import com.chen.service.UserService;

import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;
import com.chen.utils.result.UserCode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private RedisCache redisCache;
    @PostMapping("/reg")
    @ResponseBody
    public ResponseResult regist(@RequestBody User user){
        String sysCheck=redisCache.getCacheObject("captcha");
        String regCheck=user.getUserCheck();


        if(!Objects.equals(sysCheck,regCheck)){
            return new ResponseResult(UserCode.REGCHECKFAILURE);  //验证码错误
        }


        if(userService.findByName(user.getUsername())!=null){
            return new ResponseResult(UserCode.USEREXIST);   //用户已存在
        }else{
            userService.regist(user);
            userService.regInfo(user.getUid(),"新用户",1,user.getEmail(),"",new Date());
            return new ResponseResult(UserCode.REGISTSUCCESS);  //注册成功
        }
    }



//    @PostMapping("/login")
//    @ResponseBody
//    public ResponseResult login(@RequestBody User user){
//        userService.login(user);
//
//        //认证通过  通过userid生成jwt
//        return new ResponseResult(CommonCode.SUCCESS,"登陆成功");
//    }
//
}
