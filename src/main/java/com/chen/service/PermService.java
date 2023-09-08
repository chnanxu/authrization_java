package com.chen.service;


import com.chen.pojo.Authorities;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface PermService {
    List<Authorities> getAuthority(String username);
}
