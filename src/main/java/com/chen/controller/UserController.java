package com.chen.controller;

import com.chen.mapper.UserMapper;
import com.chen.pojo.User;
import com.chen.pojo.user.UserInfo;

import com.chen.utils.result.CommonCode;
import com.chen.utils.result.RedisCache;
import com.chen.utils.result.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/user")   //用户接口
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private final RedisCache redisCache;

    @Autowired
    public UserController(RedisCache redisCache){
        this.redisCache=redisCache;
    }

    @PreAuthorize("hasAuthority('system:user')")
    @RequestMapping("/home")   //个人信息主页接口
    public ResponseResult<String> userTest(@RequestHeader String token){

        User user=redisCache.getCacheObject("user:"+token);
        UserInfo userInfo=userMapper.findUserInfo(user.getUid());
        return new ResponseResult(CommonCode.SUCCESS,userInfo);
    }


    @RequestMapping("/item")  //用户主页列表项接口
    public ResponseResult<String> userItem(){
        List<String> userItem=userMapper.findUserItem();

        return new ResponseResult(CommonCode.SUCCESS,userItem);
    }


    @PreAuthorize("hasAuthority('system:user')")
    @RequestMapping("/upload/userImg")  //更新头像接口
    @ResponseBody
    public ResponseResult upload(@RequestParam("file") MultipartFile file,@RequestHeader String token) throws Exception{

        String fileName=file.getOriginalFilename();

        String newFileName=System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));

        User user=redisCache.getCacheObject("user:"+token);

        String uid=user.getUid();

        String path="D:\\Workspace\\img\\user_data\\"+uid;


        File f=new File(path);

        if(!f.exists()){
            f.mkdirs();
        }

        String url="http://localhost:9999/user_data/"+uid+"/"+newFileName;

        userMapper.updateUserImg(uid,"images/user_data/"+uid+"/"+newFileName);

        try {
            file.transferTo(new File(path+"\\"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();

            return new ResponseResult(CommonCode.FAIL,"异常情况");
        }

        return new ResponseResult(CommonCode.SUCCESS,url);

    }


    @PreAuthorize("hasAuthority('system:user')")
    @RequestMapping("/updateUserInfo")  //更新用户个人信息
    @ResponseBody
    public ResponseResult updateUserInfo(@RequestBody UserInfo userInfo){

        userMapper.updateUserInfo(userInfo);
        return new ResponseResult(CommonCode.SUCCESS,userInfo);
    }
}
