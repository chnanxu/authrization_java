package com.chen.controller;


import com.chen.mapper.UserMapper;
import com.chen.pojo.User;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getUser")
    public ResponseResult getUser(){

        List<User> userData=userMapper.findAll();
        return new ResponseResult(CommonCode.SUCCESS,userData);
    }
}
