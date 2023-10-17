package com.chen.controller;


import com.chen.service.PageService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
