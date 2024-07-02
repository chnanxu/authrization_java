package com.chen.controller;


import com.chen.pojo.page.Item_Details;
import com.chen.service.IndexService;
import com.chen.service.IndexServiceImpl;

import com.chen.utils.result.CommonCode;
import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @GetMapping({"/","/index"})
    @ResponseBody
    public ResponseResult<Item_Details> load(){

                List<Item_Details> result=indexService.getIndex();

            return new ResponseResult(CommonCode.SUCCESS,result);


    }

    @GetMapping(value={"/getAnnouncement/{announcementCommunitySortType}/{announcementSortType}"})
    public ResponseResult getAnnouncement(@PathVariable(required = false) String announcementCommunitySortType,@PathVariable(required = false) String announcementSortType){

        Map<String,List<Item_Details>> result=indexService.getAnnouncement(announcementCommunitySortType,announcementSortType);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getAnnouncementByCommunityId/{community_name}/{announcementSortType}")
    public ResponseResult getAnnouncementByCommunityName(@PathVariable String community_name,@PathVariable String announcementSortType){

        List<Item_Details> result=indexService.getAnnouncementByCommunityName(community_name,announcementSortType);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/user/item")  //用户主页列表项接口
    public ResponseResult<String> userItem(){
        List<String> userItem=indexService.findUserItem();

        return new ResponseResult(CommonCode.SUCCESS,userItem);
    }

    @GetMapping("/create/leftItem")  //
    public ResponseResult createLeftItem(){

        List<String> leftItem=indexService.finCreateLeftItem();

        return new ResponseResult(CommonCode.SUCCESS,leftItem);
    }

    @GetMapping(value = {"/getSearchTempList/{text}","getSearchTempList/"})
    public ResponseResult getSearchTempList(@PathVariable(required = false) String text){
        List<String> result=new ArrayList<>();
        if(text==null){

        }else{
            result=indexService.searchTempList(text);
        }

        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @GetMapping(value={"/getSearchDetails/{keywords}","/getSearchDetails/"})
    public ResponseResult getSearchDetails(@PathVariable(required = false) String keywords){

        List<Item_Details>  result=indexService.getSearchDetails(keywords);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

}
