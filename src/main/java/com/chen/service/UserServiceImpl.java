package com.chen.service;


import com.chen.mapper.PermMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.Authorities;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermMapper permMapper;

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

    //返回认证用户信息对象
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user=findByName(username);
        if(user==null){
            throw new UsernameNotFoundException("用户未找到");
        }

        //根据用户名查找权限
        List<Authorities> permissions=permMapper.getAuthority(username);

        //权限列表
        List<String> permList=permissions.stream().map(Authorities::getAuthority).collect(Collectors.toList());

        //为用户赋予权限标识
        user.setAuthorities(AuthorityUtils.createAuthorityList(permList));

        return user;
    }
}
