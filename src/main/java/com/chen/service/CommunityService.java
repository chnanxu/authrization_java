package com.chen.service;

import com.chen.pojo.community.Community_Details;
import com.chen.pojo.page.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityService {

    List<Group> getGroup();

    Community_Details createProject(Community_Details item);

}
