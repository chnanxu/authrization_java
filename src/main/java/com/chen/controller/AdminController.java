package com.chen.controller;


import com.chen.mapper.AdminMapper;
import com.chen.pojo.Role;
import com.chen.pojo.User;
import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.service.AdminService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('system:admin')")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminMapper adminMapper;

    private final AdminService adminService;

    @GetMapping("/getUser/{pageNum}")   //批量获取用户进行管理
    public ResponseResult getUser(@PathVariable int pageNum){

        List<User> userData=adminMapper.getUser(pageNum*14-14);



        return new ResponseResult(CommonCode.SUCCESS,userData);
    }

    @GetMapping("/getRole/{pageNum}")
    public ResponseResult getRole(@PathVariable int pageNum){

        List<Role> result=adminMapper.getRole(pageNum*10-10);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }


    @PostMapping("/agreeProject/{uid}/{pid}")    //审核通过
    public ResponseResult agreeProject(@PathVariable String uid,@PathVariable long pid){

        String message=adminService.agreeProject(uid,pid);

        return new ResponseResult(CommonCode.SUCCESS,message);
    }

    @PostMapping("/refuseProject/{uid}/{pid}")
    public ResponseResult refuseProject(@PathVariable String uid,@PathVariable long pid){

        return new ResponseResult(CommonCode.SUCCESS, adminService.refuseProject(uid,pid));
    }

    @PostMapping("/takeoffProject/{pid}")   //下架作品
    public ResponseResult takeoffProject(@PathVariable long pid){

        int code=adminService.takeoffProject(pid);
        String message="";
        if(code==0){
            message="下架成功";
        }else{
            message="下架失败";
        }

        return new ResponseResult(CommonCode.SUCCESS,message);
    }

    @PostMapping("/deleteProject/{pid}")     //删除作品
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



    @GetMapping("/getTempProject/{pageNum}")    //获取待审核作品
    public ResponseResult getTempProject(@PathVariable int pageNum){

        List<Item_Details_Temp> tempData=adminService.getTempProject(pageNum);

        return new ResponseResult(CommonCode.SUCCESS,tempData);
    }
    @GetMapping("/getProject/{pageNum}")        //获取已发布作品
    public ResponseResult getProject(@PathVariable int pageNum){

        List<Item_Details> result= adminService.getProject(pageNum);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getDeletedProject/{pageNum}")     //获取已删除作品
    public ResponseResult getDeletedProject(@PathVariable int pageNum){

        List<Item_Details> result=adminService.getDeletedProject(pageNum);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @GetMapping("/getTakeoffProject/{pageNum}") //获取已下架作品
    public ResponseResult getTakeoffProject(@PathVariable int pageNum){

        List<Item_Details> result=adminService.getTakeoffProject(pageNum);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

    @PostMapping("/reCoverProject/{pid}")
    public ResponseResult reCoverProject(@PathVariable long pid){

        adminMapper.reCoverProject(pid);

        return new ResponseResult(CommonCode.SUCCESS,"success");
    }

    @GetMapping("/getCommunity")    //获取社区
    public ResponseResult getCommunity(){

        List<Community> result=adminMapper.getCommunity();

        return new ResponseResult(CommonCode.SUCCESS,result);
    }

}
