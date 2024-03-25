package com.chen.controller;


import com.chen.mapper.AdminMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.User;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('system:admin')")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/getUser")
    public ResponseResult getUser(){

        List<User> userData=userMapper.findAll();

        return new ResponseResult(CommonCode.SUCCESS,userData);
    }

    @GetMapping("/getTempProject")
    public ResponseResult getTempProject(){

        List<Item_Details_Temp> tempData=adminMapper.getTempProject();

        return new ResponseResult(CommonCode.SUCCESS,tempData);
    }

    @PostMapping("/agreeProject/{uid}/{pid}")
    public ResponseResult agreeProject(@PathVariable String uid,@PathVariable String pid){

        Item_Details_Temp temp_item=adminMapper.getTempProjectById(uid,pid);

        temp_item.setHref("/details/"+temp_item.getType_id()+"/"+pid);

        adminMapper.setProject(temp_item);


        String result="sucess";
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getProject/{pageNum}")
    public ResponseResult getProject(@PathVariable int pageNum){

        List<Item_Details> result= adminMapper.getProject(pageNum*10-10);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }
}
