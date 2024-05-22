package com.chen.mapper;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.pojo.User;
import com.chen.pojo.page.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import com.chen.pojo.user.UserLikeDetails;
import com.chen.pojo.user.User_likeuser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends IService<User> {

    int registByEmail(User user);  //注册用户

    int registByPhone(User user);

    User findByName(String username);  //

    User findByUid(String uid);



    int updateUserImg(String uid,String user_img);  //更新头像

    int updateUserInfo(User userInfo);   //更新用户信息


    int addUserLikeComment(String uid,long pid,long comment_id);  //点赞

    int deleteUserLikeComment(String uid,long pid,long comment_id);  //取消点赞



    int createNewProject(Item_Details_Temp temp_item);

    List<Item_Details> getMyProject(String uid);

    void addLikeCommunity(String uid, long gid);

    Community getUserLikeCommunity(String uid, long gid);

    void removeCommunity(String uid, long gid);

    UserLikeDetails getUserLikeDetails(String uid, long pid);

    void deleteUserLikeDetails(String uid, long pid);

    void addUserLikeDetails(String uid, long pid,String like_time);

    List<Community> getUserLikeGroup(String uid);

    List<Community> getUserRecentLookCommunity(String uid);

    Community getUserLookCommunity(String uid, long gid);

    void insertUserLookCommunity(String uid,long gid,String last_sign_time);

    void updateUserSignTime(String uid,long gid,String last_sign_time);



    User_likeuser getUserLikeUser(String uid,String like_uid);
    void likeUser(String uid,String like_uid);
    void cancelLikeUser(String uid,String like_uid);

    UserLikeDetails getUserLikeProject(String uid,long pid);

}
