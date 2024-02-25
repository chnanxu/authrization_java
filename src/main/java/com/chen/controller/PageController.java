package com.chen.controller;


import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.UserLikeComment;
import com.chen.service.PageService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("permitAll()")
@RestController
public class PageController {

    @Autowired
    private PageService pageService;


    @GetMapping("/getHeaderItem")
    public ResponseResult getHeaderItem(){

        List<String> result=pageService.getHeaderItem();
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getGroup")  //社区接口
    public ResponseResult getGroup(){

        List<String> result=pageService.getGroup();
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PostMapping("/getPageDetails")  //详细页面数据接口
    public ResponseResult getPageDetails(@RequestBody String pid){


        Item_Details result=pageService.getPageDetails(pid);
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PostMapping("/getPageDetailsComments")  //评论数据接口
    public ResponseResult getPageDetailsComments(@RequestBody String pid){

        List<Item_Comments> result=pageService.getPageDetailsComments(pid);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/submitComment")    //提交评论
    public ResponseResult submitComment(@RequestBody Item_Comments commentData){

        pageService.submitComment(commentData);

        return new ResponseResult(CommonCode.SUCCESS,"1");
    }


    @PreAuthorize("hasAuthority('system:user')")
    @PostMapping("/onLikeAdd")   //点赞
    public ResponseResult onLikeAdd(@RequestBody UserLikeComment userLikeComment){

        pageService.onLikeAdd(userLikeComment);

        return new ResponseResult(CommonCode.SUCCESS);
    }

}
