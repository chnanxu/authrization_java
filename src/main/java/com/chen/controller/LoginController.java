package com.chen.controller;

import com.chen.pojo.User;
import com.chen.service.UserDetailServiceImpl;
import com.chen.service.UserService;

import com.chen.utils.result.ResponseResult;
import com.chen.utils.result.UserCode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

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
//    public ResponseResult login(User user){
//        //AuthenticationManager authenticate() 进行用户认证
//        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//
//        //如果认证没通过 则返回提示
//        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("登录失败");
//        }
//
//        //认证通过  通过userid生成jwt
//        return new ResponseResult(CommonCode.SUCCESS,"登陆成功");
//    }

}
