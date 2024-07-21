package com.chen.controller;


import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.OnlineUser;
import com.chen.service.CommunityService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@PreAuthorize("permitAll()")
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    private static Queue<OnlineUser> userQueue=new LinkedList<>();

    //社区相关接口--------------------------------------------------------------------------------------------------------
    @GetMapping("/getGroup")  //社区接口
    public ResponseResult getGroup(){

        List<Community> result=communityService.getCommunity();
        return new ResponseResult(CommonCode.SUCCESS,result);
    }
    @GetMapping("/getTotalHotCommunity")  //获取热门社区
    public ResponseResult getTotalHotCommunity(){

        List<Map> result=communityService.getTotalHotCommunity();

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getCommunityDetails/{id}/{sortType}/{pageNum}")    //社区详情
    public ResponseResult getCommunityDetails(@PathVariable long id,@PathVariable String sortType,@PathVariable int pageNum){

        List<Item_Details> result=communityService.getCommunityDetailsBySortType(id,pageNum,sortType);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PostMapping({"/joinUser","/pushUser"})
    public ResponseResult onlineUserList(@RequestBody(required = false) OnlineUser user){

        userQueue.offer(user);

        return new ResponseResult(CommonCode.SUCCESS,userQueue);

    }

}
