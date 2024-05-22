package com.chen.controller;


import com.chen.mapper.CommunityMapper;
import com.chen.pojo.community.Community_Details;
import com.chen.pojo.page.Community;
import com.chen.service.CommunityService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("permitAll()")
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityMapper communityMapper;
    private final CommunityService communityService;

    //社区相关接口--------------------------------------------------------------------------------------------------------
    @GetMapping("/getGroup")  //社区接口
    public ResponseResult getGroup(){

        List<Community> result=communityService.getGroup();
        return new ResponseResult(CommonCode.SUCCESS,result);
    }
    @GetMapping("/getTotalHotCommunity")  //获取热门社区
    public ResponseResult getTotalHotCommunity(){

        List<Community> result=communityMapper.getTotalHotCommunity();

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PostMapping("/submitCommunityPost")
    public ResponseResult createProject(@RequestBody Community_Details item){

        String message="";

        communityService.createProject(item);


        return new ResponseResult(CommonCode.SUCCESS,message);

    }
}
