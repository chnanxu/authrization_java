package com.chen.controller;

import com.chen.pojo.User;
import com.chen.service.UserDetailServiceImpl;
import com.chen.service.UserService;

import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import com.chen.utils.result.UserCode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/reg")
    @ResponseBody
    public ResponseResult regist(@RequestBody User user){
        if(userService.findByName(user.getUsername())!=null){
            return new ResponseResult(UserCode.USEREXIST);
        }else{
            userService.regist(user);
            return new ResponseResult(UserCode.REGISTSUCCESS);
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
