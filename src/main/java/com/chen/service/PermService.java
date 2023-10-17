package com.chen.service;


import com.chen.pojo.Permissions;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface PermService {
    List<Permissions> getAuthority(String uid);
}
