package com.chen.service;

import com.chen.mapper.PermMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.Permissions;
import com.chen.pojo.LoginUser;
import com.chen.pojo.User;
import com.chen.utils.result.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermMapper permMapper;

    @Autowired
    private RedisCache  redisCache;

    //返回认证用户信息对象

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.findByName(username);
        if(user==null){
            throw new UsernameNotFoundException("用户未找到");
        }

        //根据uid查找角色  角色对应权限
        List<Permissions> permissions=permMapper.getAuthority(user.getUid());

        //权限列表
        List<String> permList=permissions.stream().map(Permissions::getAuthority).collect(Collectors.toList());

        redisCache.setCacheObject("login:"+user.getUid(),new LoginUser(user,permList));

        //为用户赋予权限标识
        return new LoginUser(user,permList);
    }
}
