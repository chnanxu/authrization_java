package com.chen.controller;

import com.chen.mapper.UserMapper;
import com.chen.pojo.User;
import com.chen.pojo.page.Group;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;

import com.chen.pojo.user.Oauth2UserinfoResult;
import com.chen.service.UserDetailService;
import com.chen.service.UserService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@PreAuthorize("hasAuthority('system:user')")
@RestController
@RequestMapping("/user")   //用户接口
public class UserController {


    private final UserMapper userMapper;

    private final UserService userService;

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

    //社区访问日志
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

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message= userService.updateHeadImg(file,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);

    }




    @RequestMapping("/updateUserInfo")  //更新用户个人信息
    @ResponseBody
    public ResponseResult updateUserInfo(@RequestBody User userInfo){

        userMapper.updateUserInfo(userInfo);

        return new ResponseResult(CommonCode.SUCCESS,userMapper.findByUid(userInfo.getUid()));
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

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message= userService.newCoverImg(create_id,file,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);
    }


    @PostMapping("/create/newProjectImg/{create_id}/{img_id}")  //内容图片上传接口
    public ResponseResult newProjectImg(@PathVariable String create_id,@PathVariable String img_id,@RequestParam("file") MultipartFile file) throws Exception{

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message=userService.newProjectImg(create_id,img_id,file,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);
    }


    @PostMapping("/create/newProject/{create_id}")  //提交新作品接口
    public ResponseResult newProject(@PathVariable String create_id,@RequestBody Item_Details_Temp temp_item, @RequestHeader String token){

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message=userService.newProject(temp_item,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);
    }

    @PostMapping("/create/getMyProject/{uid}")  //获取作品接口
    public ResponseResult newProject(@PathVariable String uid){

        List<Item_Details> result= userMapper.getMyProject(uid);
        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @PostMapping("/likeCommunity/{gid}/{uid}")  //用户关注社区
    public ResponseResult likeCommunity(@PathVariable long gid,@PathVariable String uid){

        String message=userService.guanzhuCommunity(uid,gid);

        return new ResponseResult(CommonCode.SUCCESS,message);
    }


    @PostMapping("/likeUser/{uid}")  //用户关注作者
    public ResponseResult likeUser(@PathVariable String uid){

        Oauth2UserinfoResult loginUser=userDetailService.getLoginUserInfo();
        String message= userService.guanzhuUser(loginUser.getUid(),uid);

        return new ResponseResult(CommonCode.SUCCESS,message);
    }

    @GetMapping("/isLikeUser/{uid}")  //用户是否关注作者
    public ResponseResult isLikeUser(@PathVariable String uid){

        Oauth2UserinfoResult loginUser=userDetailService.getLoginUserInfo();

        if(userMapper.getUserLikeUser(loginUser.getUid(),uid)!=null){
            return new ResponseResult(CommonCode.SUCCESS,"已关注");
        }else{
            return new ResponseResult(CommonCode.SUCCESS,"未关注");
        }
    }

    @GetMapping("/isLikeProject/{pid}")  //用户是否喜欢作品
    public ResponseResult isLikeProject(@PathVariable long pid){

        Oauth2UserinfoResult loginUser=userDetailService.getLoginUserInfo();

        if(userMapper.getUserLikeProject(loginUser.getUid(),pid)!=null){
            return new ResponseResult(CommonCode.SUCCESS,"已点赞");
        }else{
            return new ResponseResult(CommonCode.SUCCESS,"未点赞");
        }

    }
}
