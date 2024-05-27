package com.chen.mapper;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.pojo.User;
import com.chen.pojo.community.Community;
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

    int registByPhone(User user);   //根据手机号注册用户

    User findByName(String username);  //

    User findByUid(String uid);



    int updateUserImg(String uid,String user_img);  //更新头像

    int updateUserInfo(User userInfo);   //更新用户信息


    int addUserLikeComment(String uid,long pid,long comment_id);  //点赞

    int deleteUserLikeComment(String uid,long pid,long comment_id);  //取消点赞


    int createNewProject(Item_Details_Temp temp_item);  //新建作品

    List<Item_Details> getMyProject(String uid);    //获取我的作品

    void addLikeCommunity(String uid, long community_id);   //关注社区

    Community getUserLikeCommunity(String uid, long community_id);  //单个查询社区是否关注

    void removeCommunity(String uid, long community_id);    //取消关注社区

    UserLikeDetails getUserLikeDetails(String uid, long pid);   //获取用户是否推荐该作品

    void deleteUserLikeDetails(String uid, long pid);   //取消推荐作品

    void addUserLikeDetails(String uid, long pid,String like_time); //推荐作品

    List<Community> getUserLikeCommunityList(String uid);   //获取用户关注社区列表

    List<Community> getUserRecentLookCommunity(String uid);  //获取用户最近访问社区列表

    Community getUserLookCommunity(String uid, long community_id);  //获取用户最近访问社区

    void insertUserLookCommunity(String uid,long community_id,String last_sign_time); //若之前没访问过该社区则插入表中

    void updateUserSignTime(String uid,long community_id,String last_sign_time);    //更新用户访问社区时间



    User_likeuser getUserLikeUser(String uid,String like_uid);
    void likeUser(String uid,String like_uid);
    void cancelLikeUser(String uid,String like_uid);

    UserLikeDetails getUserLikeProject(String uid,long pid);

}
