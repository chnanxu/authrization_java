package com.chen.controller;


import com.chen.pojo.User;
import com.chen.service.Oauth2.IQrCodeLoginService;
import com.chen.service.UserDetailService;
import com.chen.service.UserDetailServiceImpl;
import com.chen.service.UserService;

import com.chen.utils.result.CommonCode;
import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;
import com.chen.utils.result.UserCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.chen.utils.util.RedisConstants.SMS_CAPTCHA_PREFIX_KEY;

@PreAuthorize("permitAll()")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final UserDetailService userDetailService;
    private final RedisCache redisCache;

    @PostMapping("/reg")
    @ResponseBody
    public ResponseResult regist(@RequestBody User user){
        String sysCheck=redisCache.getCacheObject("captcha");
        String regCheck=user.getCaptcha();


        if(!Objects.equals(sysCheck,regCheck)){
            return new ResponseResult(UserCode.REGCHECKFAILURE);  //验证码错误
        }


        if(userService.findByName(user.getUsername())!=null){
            return new ResponseResult(UserCode.USEREXIST);   //用户已存在
        }else{

            userService.regist(user);

            return new ResponseResult(UserCode.REGISTSUCCESS);  //注册成功
        }
    }

    @ResponseBody
    @GetMapping("/getSmsCaptcha/{phone}")
    public ResponseResult getSmsCaptcha(@PathVariable("phone") String phone){
        Map<String,Object> result=new HashMap<>();
        result.put("code", HttpStatus.OK.value());
        result.put("message","获取短信验证码成功");
        result.put("data","1234");
        redisCache.setCacheObject(SMS_CAPTCHA_PREFIX_KEY+phone,"1234");
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

}

