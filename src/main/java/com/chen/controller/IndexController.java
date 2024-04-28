package com.chen.controller;


import com.chen.pojo.page.Item_Details;
import com.chen.service.IndexServiceImpl;

import com.chen.utils.result.CommonCode;
import com.chen.utils.util.RedisCache;
import com.chen.utils.result.ResponseResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class IndexController {


    @Autowired
    private IndexServiceImpl indexService;



    @GetMapping({"/","/index"})
    @ResponseBody
    public ResponseResult<Item_Details> load(){

                List<Item_Details> result=indexService.getIndex();

            return new ResponseResult(CommonCode.SUCCESS,result);


    }

    @GetMapping("/user/item")  //用户主页列表项接口
    public ResponseResult<String> userItem(){
        List<String> userItem=indexService.findUserItem();

        return new ResponseResult(CommonCode.SUCCESS,userItem);
    }

    @GetMapping("/create/leftItem")
    public ResponseResult createLeftItem(){

        List<String> leftItem=indexService.finCreateLeftItem();

        return new ResponseResult(CommonCode.SUCCESS,leftItem);
    }

    @GetMapping("/getSearchTempList/{text}")
    public ResponseResult getSearchTempList(@PathVariable String text){

        List<String> result=indexService.searchTempList(text);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }
}
