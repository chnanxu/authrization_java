package com.chen.controller;


import com.chen.mapper.AdminMapper;
import com.chen.pojo.User;
import com.chen.pojo.page.Group;
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
    private AdminMapper adminMapper;

    @GetMapping("/getUser/{pageNum}")
    public ResponseResult getUser(@PathVariable int pageNum){

        List<User> userData=adminMapper.getUser(pageNum*14-14);



        return new ResponseResult(CommonCode.SUCCESS,userData);
    }


    @PostMapping("/agreeProject/{uid}/{pid}")
    public ResponseResult agreeProject(@PathVariable String uid,@PathVariable String pid){

        Item_Details_Temp temp_item=adminMapper.getTempProjectById(uid,pid);

        temp_item.setHref("/page/details/"+temp_item.getType_id()+"/"+pid);

        adminMapper.setProject(temp_item);


        String result="sucess";
        return new ResponseResult(CommonCode.SUCCESS,result);
    }



    @GetMapping("/getTempProject/{pageNum}")
    public ResponseResult getTempProject(@PathVariable int pageNum){

        List<Item_Details_Temp> tempData=adminMapper.getTempProject(pageNum*10-10);

        return new ResponseResult(CommonCode.SUCCESS,tempData);
    }
    @GetMapping("/getProject/{pageNum}")
    public ResponseResult getProject(@PathVariable int pageNum){

        List<Item_Details> result= adminMapper.getProject(pageNum*10-10);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getCommunity")
    public ResponseResult getCommunity(){

        List<Group> result=adminMapper.getCommunity();

        return new ResponseResult(CommonCode.SUCCESS,result);
    }
}
