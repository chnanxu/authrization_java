package com.chen.service;

import com.chen.pojo.community.Community_Details;
import com.chen.pojo.page.Community;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityService {

    List<Community> getGroup();

    Community_Details createProject(Community_Details item);

}
