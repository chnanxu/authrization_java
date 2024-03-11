package com.chen.controller;

import com.chen.mapper.UserMapper;
import com.chen.pojo.User;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.pojo.user.UserInfo;

import com.chen.utils.result.CommonCode;
import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@PreAuthorize("hasAuthority('system:user')")
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


    @RequestMapping("/home")   //个人信息主页接口
    public ResponseResult<String> userTest(@RequestHeader String token){

        User user=redisCache.getCacheObject("user:"+token);
        UserInfo userInfo=userMapper.findUserInfo(user.getUid());
        return new ResponseResult(CommonCode.SUCCESS,userInfo);
    }




    @RequestMapping("/upload/userImg")  //更新头像接口
    @ResponseBody
    public ResponseResult upload(@RequestParam("file") MultipartFile file,@RequestHeader String token) throws Exception{

        String fileName=file.getOriginalFilename();


        User user=redisCache.getCacheObject("user:"+token);

        String uid=user.getUid();

        String path="D:\\Workspace\\img\\user_data\\"+uid;

        String newFileName="user_headImg"+fileName.substring(fileName.lastIndexOf("."));

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




    @RequestMapping("/updateUserInfo")  //更新用户个人信息
    @ResponseBody
    public ResponseResult updateUserInfo(@RequestBody UserInfo userInfo){

        userMapper.updateUserInfo(userInfo);

        return new ResponseResult(CommonCode.SUCCESS,userInfo);
    }


    /**
     * 创作中心
     * @param create_id
     * @param file
     * @param token
     * @return
     */
    @PostMapping("/create/newCoverImg/{create_id}") //封面上传接口
    public ResponseResult newCoverImg(@PathVariable String create_id,@RequestParam("file") MultipartFile file,@RequestHeader String token){

        User user=redisCache.getCacheObject("user:"+token);

        String uid=user.getUid();
        String id=redisCache.getCacheObject(uid+"create_id:");

        if(id==null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
            id=sdf.format(System.currentTimeMillis());
            redisCache.setCacheObject(uid+"create_id:",id);
        }
        String fileName=file.getOriginalFilename();


        String path="D:\\Workspace\\img\\user_data\\"+uid+"\\project_data\\"+create_id+"\\"+id;
        String newFileName="cover_img"+fileName.substring(fileName.lastIndexOf("."));

        File f=new File(path);
        if(!f.exists()){
            f.mkdirs();
        }

        String coverImgUrl="images/user_data/"+uid+"/project_data/"+create_id+"/"+id+"/"+newFileName;

        try {
            file.transferTo(new File(path+"\\"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL,"异常情况");
        }

        return new ResponseResult(CommonCode.SUCCESS,coverImgUrl);
    }


    @PostMapping("/create/newProjectImg/{create_id}/{img_id}")  //内容图片上传接口
    public ResponseResult newProjectImg(@PathVariable String create_id,@PathVariable String img_id,@RequestParam("file") MultipartFile file,@RequestHeader String token) throws Exception{

        User user=redisCache.getCacheObject("user:"+token);

        String uid=user.getUid();
        String id=redisCache.getCacheObject(uid+"create_id:");

        if(id==null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
            id=sdf.format(System.currentTimeMillis());
            redisCache.setCacheObject(uid+"create_id:",id);
        }

        String fileName=file.getOriginalFilename();



        String path="D:\\Workspace\\img\\user_data\\"+uid+"\\project_data\\"+create_id+"\\"+id;

        String newFileName="content_"+img_id+fileName.substring(fileName.lastIndexOf("."));

        File f=new File(path);
        if(!f.exists()){
            f.mkdirs();
        }

        String url="images/user_data/"+uid+"/project_data/"+create_id+"/"+id+"/"+newFileName;

        try {
            file.transferTo(new File(path+"\\"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL,"异常情况");
        }
        return new ResponseResult(CommonCode.SUCCESS,url);
    }


    @PostMapping("/create/newProject/{create_id}")  //提交新作品接口
    public ResponseResult newProject(@PathVariable String create_id,@RequestBody Item_Details_Temp temp_item, @RequestHeader String token){

        User user=redisCache.getCacheObject("user:"+token);
        String uid=user.getUid();

        String id=redisCache.getCacheObject(uid+"create_id:");
        temp_item.setPid(id);
        userMapper.createNewProject(temp_item);



        redisCache.deleteObject(uid+"create_id:");

        return new ResponseResult(CommonCode.SUCCESS,"success");
    }

}
