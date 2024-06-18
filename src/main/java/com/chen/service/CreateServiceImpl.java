package com.chen.service;

import com.chen.mapper.CreateMapper;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.utils.util.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateServiceImpl implements CreateService{

    private final CreateMapper createMapper;

    private final RedisCache redisCache;

    @Override
    public List<Map> getCommunityListByQueryType(String queryType,int pageNum) {

        List<Map> result;
        if(queryType.equals("first")){

            result=createMapper.getCommunityList();
        }else{
            result=createMapper.getCommunityListByType(queryType,pageNum);
        }

        return result;
    }

    @Override     //上传封面实现
    public String newCoverImg(String create_id, MultipartFile file, String uid){

        String id=redisCache.getCacheObject(uid+"create_id:");

        if(id==null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
            id=sdf.format(System.currentTimeMillis());
            redisCache.setCacheObject(uid+"create_id:",id);
        }
        String fileName=file.getOriginalFilename();


        String path="D:\\Workspace\\img\\user_data\\"+uid+"\\project_data\\"+create_id+"\\"+id;
        String newFileName="cover_img"+fileName.substring(fileName.lastIndexOf("."));

        return getString(create_id, file, uid, id, path, newFileName);
    }

    @Override     //上传内容图片实现
    public String newProjectImg(String create_id, String img_id, MultipartFile file, String uid) {

        String id=redisCache.getCacheObject(uid+"create_id:");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSSS");

        if(id==null){

            id=sdf.format(System.currentTimeMillis());
            redisCache.setCacheObject(uid+"create_id:",id);
        }

        String fileName=file.getOriginalFilename();



        String path="D:\\Workspace\\img\\user_data\\"+uid+"\\project_data\\"+create_id+"\\"+id;

        String newFileName="content_"+img_id+fileName.substring(fileName.lastIndexOf("."));

        return getString(create_id, file, uid, id, path, newFileName);
    }

    //上传本地文件方法
    private String getString(String create_id, MultipartFile file, String uid, String id, String path, String newFileName) {
        File f=new File(path);
        if(!f.exists()){
            f.mkdirs();
        }

        String url="images/user_data/"+uid+"/project_data/"+create_id+"/"+id+"/"+newFileName;

        try {
            file.transferTo(new File(path+"\\"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return "异常情况";
        }

        return url;
    }


    @Override   //上传新作品实现
    public String newProject(Item_Details_Temp temp_item, String uid) {

        try{
            String id=redisCache.getCacheObject(uid+"create_id:");
            temp_item.setPid(id);
            createMapper.createNewProject(temp_item);

            redisCache.deleteObject(uid+"create_id:");

        }catch(Exception e){
            e.printStackTrace();
            return "异常情况";
        }

        return "success";

    }

    @Override
    public List<Item_Details> getMyProject(String uid,String sortType) {
        if(sortType.equals("time")){
            return createMapper.getMyProjectByTime(uid);
        } else if (sortType.equals("hot")) {
            return createMapper.getMyProjectByHot(uid);
        } else if (sortType.equals("waitAgree")) {
            return createMapper.getMyProjectByNoAgree(uid);
        } else if(sortType.equals("draft")){
            return createMapper.getMyProjectByDraft(uid);
        }
        return null;
    }


}
