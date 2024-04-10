package com.chen.controller;

import com.chen.mapper.UserMapper;
import com.chen.pojo.User;
import com.chen.pojo.page.Group;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;

import com.chen.pojo.user.Oauth2UserinfoResult;
import com.chen.service.UserDetailService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;


@RequiredArgsConstructor
@PreAuthorize("hasAuthority('system:user')")
@RestController
@RequestMapping("/user")   //用户接口
public class UserController {


    private final UserMapper userMapper;

    private final UserDetailService userDetailService;

    private final RedisCache redisCache;



    @RequestMapping("/home")   //个人信息主页接口
    public ResponseResult<String> userTest(){

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        return new ResponseResult(CommonCode.SUCCESS,user);
    }

    @GetMapping("/getUserLikeGroup")   //获取用户关注社区接口
    public ResponseResult getUserLikeGroup(){

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        List<Group> result=userMapper.getUserLikeGroup(user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,result);

    }

    @GetMapping("/updateUserSignTime/{id}/{uid}/{sign_time}")
    public ResponseResult updateUserSignTime(@PathVariable long id,@PathVariable String uid,@PathVariable String sign_time){

        if(userMapper.getUserLookCommunity(uid,id)!=null){
            userMapper.updateUserSignTime(uid,id,sign_time);
        }else{
            userMapper.insertUserLookCommunity(uid,id,sign_time);
        }

        return new ResponseResult(CommonCode.SUCCESS,"success");
    }


    @GetMapping("/getUserRecentLookCommunity/{uid}")  //获取用户最近访问社区接口
    public ResponseResult getUserRecentLookCommunity(@PathVariable String uid, @RequestHeader String token){

        List<Group> result=userMapper.getUserRecentLookCommunity(uid);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @RequestMapping("/upload/userImg")  //更新头像接口
    @ResponseBody
    public ResponseResult upload(@RequestParam("file") MultipartFile file,@RequestHeader String token) throws Exception{

        String fileName=file.getOriginalFilename();


        User user=redisCache.getCacheObject("userInfo:"+token);

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
    public ResponseResult updateUserInfo(@RequestBody User userInfo){

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

        User user=redisCache.getCacheObject("userInfo:"+token);

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

        User user=redisCache.getCacheObject("userInfo:"+token);

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

        User user=redisCache.getCacheObject("userInfo:"+token);
        String uid=user.getUid();

        String id=redisCache.getCacheObject(uid+"create_id:");
        temp_item.setPid(id);
        userMapper.createNewProject(temp_item);



        redisCache.deleteObject(uid+"create_id:");

        return new ResponseResult(CommonCode.SUCCESS,"success");
    }

    @PostMapping("/create/getMyProject/{uid}")  //获取作品接口
    public ResponseResult newProject(@PathVariable String uid){

        List<Item_Details> result= userMapper.getMyProject(uid);
        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @PostMapping("/likeCommunity/{gid}/{uid}")  //用户关注社区
    public ResponseResult likeCommunity(@PathVariable long gid,@PathVariable String uid){

        userMapper.addLikeCommunity(uid,gid);

        return new ResponseResult(CommonCode.SUCCESS,"success");
    }

    @PostMapping("/removeCommunity/{gid}/{uid}")  //用户取消关注社区
    public ResponseResult removeCommunity(@PathVariable long gid,@PathVariable String uid){

        userMapper.removeCommunity(uid,gid);

        return new ResponseResult(CommonCode.SUCCESS,"success");
    }

}
