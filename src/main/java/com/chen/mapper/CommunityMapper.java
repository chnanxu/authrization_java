package com.chen.mapper;


import com.chen.pojo.community.Community_Details;
import com.chen.pojo.page.Group;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommunityMapper {
    List<Group> getGroup();

    List<Group> getTotalHotCommunity();

    int insertCommunity_Details(Community_Details item);

}
