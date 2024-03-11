package com.chen.mapper;


import com.chen.pojo.User;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.pojo.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    int regist(User user);  //注册用户

    int regInfo(String uid, String uname, int sex, String email, String user_img, Date signDate);  //注册用户信息



    User findByName(String username);  //

    UserInfo findUserInfo(String uid);


    int updateUserImg(String uid,String user_img);  //更新头像

    int updateUserInfo(UserInfo userInfo);   //更新用户信息


    int addUserLikeComment(String uid,long pid,long comment_id);  //点赞

    int deleteUserLikeComment(String uid,long pid,long comment_id);  //取消点赞

    List<User> findAll();

    int createNewProject(Item_Details_Temp temp_item);

}
