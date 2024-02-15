package com.chen.service;


import com.chen.mapper.UserMapper;
import com.chen.pojo.User;

import com.chen.pojo.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

//    @Resource
//    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Override
//    public User login(User user){
//        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
//        Authentication authenticate=authenticationManager.authenticate((authenticationToken));
//        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("登录失败");
//        }
//
//
//
//
//    }


    @Override
    public User findByName(String username){
        return userMapper.findByName(username);
    }

    @Override
    public List<String> findUserItem() {
        return userMapper.findUserItem();
    }

    public UserInfo findUserInfo(String uid){
        return userMapper.findUserInfo(uid);
    }

    //在数据库中，新增一位用户，且密码以加盐形式保存
    @Override
    public int regist(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.regist(user);
    }

    @Override
    public int regInfo(String uid, String name, int sex, String email, String user_img, Date signDate) {
        return userMapper.regInfo(uid,name,sex, email,user_img,signDate);
    }


    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
