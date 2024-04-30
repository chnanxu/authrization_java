package com.chen.controller;


import com.chen.pojo.User;
import com.chen.service.UserDetailServiceImpl;
import com.chen.service.UserService;

import com.chen.utils.result.CommonCode;
import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;
import com.chen.utils.result.UserCode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.chen.utils.util.RedisConstants.SMS_CAPTCHA_PREFIX_KEY;

@PreAuthorize("permitAll()")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
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
    @GetMapping("/getSmsCaptcha")
    public ResponseResult getSmsCaptcha(@RequestParam("phone") String phone){
        Map<String,Object> result=new HashMap<>();
        result.put("code", HttpStatus.OK.value());
        result.put("message","获取短信验证码成功");
        result.put("data","1234");
        redisCache.setCacheObject(SMS_CAPTCHA_PREFIX_KEY+phone,"1234");
        return new ResponseResult(CommonCode.SUCCESS,result);
    }



}
