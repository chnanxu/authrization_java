package com.chen.controller;

import com.chen.pojo.Index;
import com.chen.service.IndexServiceImpl;

import com.chen.utils.result.CommonCode;
import com.chen.utils.result.RedisCache;
import com.chen.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class IndexController {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IndexServiceImpl indexService;


    @RequestMapping({"/","/index"})
    @ResponseBody
    public ResponseResult<Index> load(){


                List<Index> movies=indexService.getMovies(0,10);

            return new ResponseResult(CommonCode.SUCCESS,movies);


    }


}
