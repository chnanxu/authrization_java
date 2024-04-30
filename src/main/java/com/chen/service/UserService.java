package com.chen.service;


import com.chen.pojo.User;
import com.chen.pojo.page.Item_Details_Temp;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    int regist(User user);

    User findByName(String username);

    String guanzhuUser(String uid,String like_uid);

    String guanzhuCommunity(String uid,long gid);

    String updateHeadImg(MultipartFile file,String uid);

    String newCoverImg(String create_id,MultipartFile file,String uid);

    String newProjectImg(String create_id,String img_id,MultipartFile file,String uid);

    String newProject(Item_Details_Temp temp_item,String uid);

}
