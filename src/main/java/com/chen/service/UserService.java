package com.chen.service;


import com.chen.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    User regist(User user);

    User findByName(String username);


    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
