package com.chen;

import com.chen.pojo.User;
import com.chen.service.PermService;
import com.chen.service.UserService;
import com.chen.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class MovieApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void test(){
        System.out.println(userService.findAll());
    }
}
