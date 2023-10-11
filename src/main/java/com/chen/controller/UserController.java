package com.chen.controller;


import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class UserController {



    @PreAuthorize("hasAuthority('system:user')")
    @RequestMapping("/home")
    public ResponseResult<String> userTest(){

        return new ResponseResult(CommonCode.SUCCESS,"成功");
    }
}
