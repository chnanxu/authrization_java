package com.chen.service;

import com.chen.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminMapper adminMapper;

    @Override
    public String refuseProject(String uid, long pid) {
        if(adminMapper.refuseProjectById(uid,pid)==1){
            return "操作成功";
        }else{
            return "操作失败";
        }
    }
}
