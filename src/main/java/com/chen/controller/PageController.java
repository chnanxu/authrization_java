package com.chen.controller;


import com.chen.mapper.PageMapper;
import com.chen.pojo.page.*;
import com.chen.pojo.user.UserLikeComment;
import com.chen.service.PageService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("permitAll()")
@RestController
@RequiredArgsConstructor
public class PageController {

    private final PageMapper pageMapper;

    private final PageService pageService;

    //常规页面相关接口--------------------------------------------------------------------------------------------------------

    @GetMapping("/getHeaderItem")  //导航栏
    public ResponseResult getHeaderItem(){

        List<String> result=pageService.getHeaderItem();
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getTypeList")  //类型列表
    public ResponseResult getTypeList(){

        List<All_Type> result=pageService.getTypeList();
        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @GetMapping("/getLeftNavbar") //左侧边栏
    public ResponseResult getLeftNavbar(){

        List<String> result=pageService.getLeftNavbar();

        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @RequestMapping("/getPageDetails/{typeid}/{pid}")  //详细页面数据接口
    public ResponseResult getPageDetails(@PathVariable int typeid,@PathVariable long pid){


        Item_Details result=pageService.getPageDetails(pid);
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PostMapping("/getPageDetailsComments/{typeid}/{pid}")  //评论数据接口
    public ResponseResult getPageDetailsComments(@PathVariable int typeid,@PathVariable long pid,@RequestHeader String token){

        List<Item_Comments> result=pageService.getPageDetailsComments(pid,token);

        if(result==null){
            return new ResponseResult(CommonCode.SUCCESS,"当前没有评论");
        }

        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @PostMapping("/getAllSonComment")   //获取所有子评论
    public ResponseResult getAllSonComment(@RequestBody long comment_id){


        List<Item_Comments> result=pageMapper.getAllSonComment(comment_id);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @PostMapping("/getReCommentUname/{typeid}/{to_commentID}")  //获取回复用户昵称
    public ResponseResult getReCommentUname(@PathVariable int typeid,@PathVariable long to_commentID){

        String result= pageService.getReCommentUname(to_commentID);
        return new ResponseResult(CommonCode.SUCCESS,result);
    }






    //用户相关接口--------------------------------------------------------------------------------------------------------


    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/submitCommunityPost/{gid}")
    public ResponseResult submitCommunityPost(@PathVariable String gid){

        return new ResponseResult(CommonCode.SUCCESS,"success");
    }

    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/submitComment")    //提交评论
    public ResponseResult submitComment(@RequestBody Item_Comments commentData){

        pageService.submitComment(commentData);

        return new ResponseResult(CommonCode.SUCCESS,"1");
    }

    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/submitReComment")  //回复评论
    public ResponseResult submitReComment(@RequestBody Item_Comments commentData){

        pageService.submitReComment(commentData);

        return new ResponseResult(CommonCode.SUCCESS,"");
    }

    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/deleteComment")   //删除评论
    public ResponseResult deleteComment(@RequestBody long comment_id){

        pageService.deleteComment(comment_id);

        return new ResponseResult(CommonCode.SUCCESS,"删除评论");
    }


    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/onLikeComment")   //点赞评论
    public ResponseResult onLikeComment(@RequestBody UserLikeComment userLikeComment){

        String message= pageService.onLikeComment(userLikeComment);

        return new ResponseResult(CommonCode.SUCCESS,message);
    }

    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/likeDetails/{uid}/{pid}")  //点赞作品
    public ResponseResult likeDetails(@PathVariable String uid,@PathVariable long pid){

        String message= pageService.onLikeDetails(uid,pid);

        return new ResponseResult(CommonCode.SUCCESS,message);
    }



}
