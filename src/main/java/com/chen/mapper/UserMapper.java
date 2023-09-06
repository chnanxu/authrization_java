package com.chen.mapper;


import com.chen.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User regist(User user);

    User findByName(String username);

    User findByUnameAndPwd(String username,String password);
    List<User> findAll();
}
