package com.chen.controller;

import com.chen.service.CreateService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAuthority('system:user')")
@RestController
@RequiredArgsConstructor
public class CreateController {

    private final CreateService createService;

    @GetMapping("/getCommunityList/{queryType}/{pageNum}")
    public ResponseResult getCommunityList(@PathVariable String queryType,@PathVariable int pageNum){

        return new ResponseResult(CommonCode.SUCCESS,createService.getCommunityListByQueryType(queryType,pageNum));
    }

}
