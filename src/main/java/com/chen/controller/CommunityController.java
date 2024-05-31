package com.chen.controller;


import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.service.CommunityService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@PreAuthorize("permitAll()")
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

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



    @GetMapping("/getCommunityDetails/{id}/{pageNum}/{sortType}")
    public ResponseResult getCommunityDetails(@PathVariable long id,@PathVariable int pageNum,@PathVariable String sortType){

        List<Item_Details> result=communityService.getCommunityDetailsBySortType(id,pageNum,sortType);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

}
