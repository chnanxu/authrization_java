package com.chen.service;


import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    String refuseProject(String uid,long pid);
}
