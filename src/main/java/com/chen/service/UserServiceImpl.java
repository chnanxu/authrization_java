package com.chen.service;


import com.chen.mapper.UserMapper;
import com.chen.pojo.User;

import com.chen.pojo.page.Item_Details_Temp;
import com.chen.utils.util.RedisCache;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RedisCache redisCache;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;



    @Override
    public User findByName(String username){
        return userMapper.findByName(username);
    }


    //在数据库中，新增一位用户，且密码以加盐形式保存
    @Override
    public int regist(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getEmail()==null){
            return userMapper.registByEmail(user);
        }else{
            return userMapper.registByPhone(user);
        }


    }

    @Override   //关注作者实现
    public String guanzhuUser(String uid,String like_uid){
        String message="";
        if(userMapper.getUserLikeUser(uid,like_uid)!=null){
            userMapper.cancelLikeUser(uid,like_uid);
            message="已取消关注";
        }else{
            userMapper.likeUser(uid,like_uid);
            message="已关注";
        }
        return message;
    }

    @Override   //关注社区实现
    public String guanzhuCommunity(String uid,long gid){
        String message="";
        if(userMapper.getUserLikeCommunity(uid,gid)!=null){
            userMapper.removeCommunity(uid,gid);
            message="已取消关注";
        }else{
            userMapper.addLikeCommunity(uid,gid);
            message="已关注";
        }
        return message;
    }

    @Override    //更新头像实现
    public String updateHeadImg(MultipartFile file,String uid){

        String fileName=file.getOriginalFilename();

        String path="D:\\Workspace\\img\\user_data\\"+uid;

        String newFileName="user_headImg"+fileName.substring(fileName.lastIndexOf("."));

        File f=new File(path);

        if(!f.exists()){
            f.mkdirs();
        }

        String url="http://localhost:9999/user_data/"+uid+"/"+newFileName;

        userMapper.updateUserImg(uid,"images/user_data/"+uid+"/"+newFileName);

        try {
            file.transferTo(new File(path+"\\"+newFileName));
        } catch (IOException e) {
            e.printStackTrace();

            return "异常情况";
        }

        return url;
    }

    @Override     //上传封面实现
    public String newCoverImg(String create_id,MultipartFile file,String uid){

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
            userMapper.createNewProject(temp_item);

            redisCache.deleteObject(uid+"create_id:");

        }catch(Exception e){
            e.printStackTrace();
            return "异常情况";
        }

        return "success";

    }
}
