package com.chen.service;


import com.chen.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface UserService {
    int regist(User user);

    int regInfo(String uid, String name, int sex, String email, String user_img, Date signDate);
//    User login(User user);

    User findByName(String username);

    List<String> findUserItem();

    List<User> findAll();

}
