package com.chen.controller;

import com.chen.pojo.User;
import com.chen.service.UserServiceImpl;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/reg")
    @ResponseBody
    public ResponseResult<String> regist(@RequestBody  User user){
        if(userService.findByName(user.getUsername())!=null){
            return new ResponseResult(CommonCode.FAIL,"账号已存在");
        }else{
            userService.regist(user);
            return new ResponseResult(CommonCode.SUCCESS,"注册成功");
        }
    }

    @RequestMapping("/user/home")
    public ResponseResult<String> userTest(){
        return new ResponseResult(CommonCode.SUCCESS,"测试");
    }
}
