package com.chen.service;


import com.chen.pojo.User;
import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details_Temp;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    int regist(User user);

    User findByName(String username);

    String guanzhuUser(String uid,String like_uid);

    String guanzhuCommunity(String uid,long gid);

    String updateHeadImg(MultipartFile file,String uid);

    List<Community> getUserLikeCommunity(String uid);

}
