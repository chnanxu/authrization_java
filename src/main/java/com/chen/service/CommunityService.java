package com.chen.service;

import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CommunityService {

    List<Community> getCommunity();

    List<Map> getTotalHotCommunity();


    List<Item_Details> getCommunityDetailsBySortType(long community_id,int pageNum,String sortType);

}
