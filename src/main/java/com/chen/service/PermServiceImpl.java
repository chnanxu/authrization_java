package com.chen.service;

import com.chen.mapper.PermMapper;
import com.chen.pojo.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermServiceImpl implements PermService{

    @Autowired
    PermMapper permMapper;
    @Override
    public List<Permissions> getAuthority(String username) {
        return permMapper.getAuthority(username);
    }
}
