package com.chen.mapper;


import com.chen.pojo.User;
import com.chen.pojo.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User regist(User user);

    User findByName(String username);

    UserInfo findUserInfo(String uid);

    User findByUnameAndPwd(String username,String password);
    List<User> findAll();
}
