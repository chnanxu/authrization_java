package com.chen.mapper;


import com.chen.pojo.User;
import com.chen.pojo.user.UserInfo;
import com.chen.pojo.user.UserItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User regist(User user);

    User findByName(String username);

    UserInfo findUserInfo(String uid);

    List<String> findUserItem();

    String updateUserImg(String uid,String user_img);

    List<User> findAll();
}
