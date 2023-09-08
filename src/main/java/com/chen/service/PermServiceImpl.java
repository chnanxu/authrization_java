package com.chen.service;

import com.chen.mapper.PermMapper;
import com.chen.pojo.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermServiceImpl implements PermService{

    @Autowired
    PermMapper permMapper;
    @Override
    public List<Authorities> getAuthority(String username) {
        return permMapper.getAuthority(username);
    }
}
