package com.chen.controller;

import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.pojo.user.Oauth2UserinfoResult;
import com.chen.service.CreateService;
import com.chen.service.UserDetailService;
import com.chen.utils.result.CommonCode;
import com.chen.utils.result.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@PreAuthorize("hasAuthority('system:user')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class CreateController {

    private final CreateService createService;

    private final UserDetailService userDetailService;


    @GetMapping("/getCommunityList/{queryType}/{pageNum}")
    public ResponseResult getCommunityList(@PathVariable String queryType,@PathVariable int pageNum){

        return new ResponseResult(CommonCode.SUCCESS,createService.getCommunityListByQueryType(queryType,pageNum));
    }

    /**
     * 创作中心
     * @param create_id
     * @param file
     * @param token
     * @return
     */
    @PostMapping("/create/newCoverImg/{create_id}") //封面上传接口
    public ResponseResult newCoverImg(@PathVariable String create_id, @RequestParam("file") MultipartFile file, @RequestHeader String token){

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message= createService.newCoverImg(create_id,file,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);
    }


    @PostMapping("/create/newProjectImg/{create_id}/{img_id}")  //内容图片上传接口
    public ResponseResult newProjectImg(@PathVariable String create_id,@PathVariable String img_id,@RequestParam("file") MultipartFile file){

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message=createService.newProjectImg(create_id,img_id,file,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);
    }


    @PostMapping("/create/newProject/{create_id}")  //提交新作品接口
    public ResponseResult newProject(@PathVariable String create_id, @RequestBody Item_Details_Temp temp_item, @RequestHeader String token){

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message=createService.newProject(temp_item,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);
    }

    @PostMapping("/create/saveTempProject")   //保存草稿
    public ResponseResult saveTempProject(@RequestBody Item_Details_Temp temp_item){
        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        String message=createService.newProject(temp_item,user.getUid());

        return new ResponseResult(CommonCode.SUCCESS,message);
    }


    @PostMapping("/create/getMyProject/{uid}/{sortType}")  //获取作品接口
    public ResponseResult newProject(@PathVariable String uid,@PathVariable String sortType){

        List<Item_Details> result= createService.getMyProject(uid,sortType);

        return new ResponseResult(CommonCode.SUCCESS,result);
    }



}
