package com.chen.service;


import com.chen.mapper.UserMapper;
import com.chen.pojo.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public User findByName(String username){
        return userMapper.findByName(username);
    }


    //在数据库中，新增一位用户，且密码以加盐形式保存
    @Override
    public User regist(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.regist(user);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
