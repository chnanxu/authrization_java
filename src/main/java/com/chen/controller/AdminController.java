package com.chen.controller;


import com.chen.mapper.AdminMapper;
import com.chen.pojo.User;
import com.chen.pojo.community.Community_Details;
import com.chen.pojo.page.Group;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.service.AdminService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('system:admin')")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminMapper adminMapper;



    @GetMapping("/getUser/{pageNum}")
    public ResponseResult getUser(@PathVariable int pageNum){

        List<User> userData=adminMapper.getUser(pageNum*14-14);



        return new ResponseResult(CommonCode.SUCCESS,userData);
    }


    @PostMapping("/agreeProject/{uid}/{pid}")
    public ResponseResult agreeProject(@PathVariable String uid,@PathVariable long pid){

        Item_Details_Temp temp_item=adminMapper.getTempProjectById(uid,pid);

        temp_item.setHref("/page/details/"+temp_item.getType_id()+"/"+pid);

        adminMapper.setProject(temp_item);


        String result="sucess";
        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PostMapping("/deleteProject/{pid}")
    public ResponseResult deleteProject(@PathVariable long pid){
        int code= adminMapper.deleteProjectById(pid);
        String message="";
        if(code==0){
            message="删除成功";
        }else{
            message="删除失败";
        }
        return new ResponseResult(CommonCode.SUCCESS,message);
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

    @GetMapping("/getDeletedProject/{pageNum}")
    public ResponseResult getDeletedProject(@PathVariable int pageNum){

        List<Item_Details> result=adminMapper.getDeletedProject(pageNum*10-10);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getCommunity")
    public ResponseResult getCommunity(){

        List<Group> result=adminMapper.getCommunity();

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getCommunityDetails/{community_id}")
    public ResponseResult getCommunityDetails(@PathVariable long community_id){

        List<Community_Details> result=adminMapper.getCommunityDetails(community_id);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getTempCommunityDetails")
    public ResponseResult getTempCommunityDetails(){

        List<Community_Details> result=adminMapper.getTempCommunityDetails();

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

}
