package com.chen.service;


import com.chen.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface UserService {
    int regist(User user);


    User findByName(String username);



}
