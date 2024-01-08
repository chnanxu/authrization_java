package com.chen.service;


import com.chen.pojo.User;
import com.chen.pojo.user.UserItem;
import com.chen.utils.result.ResponseResult;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User regist(User user);

    User findByName(String username);

    List<String> findUserItem();

    List<User> findAll();

}
