package com.chen;

import com.chen.pojo.User;
import com.chen.service.PermService;
import com.chen.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class MovieApplicationTests {

    @Autowired
    PermService permService;
    @Test
    public void test(){
        System.out.println(permService.getAuthority("cx12345"));
    }
}
