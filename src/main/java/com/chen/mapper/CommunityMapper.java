package com.chen.mapper;


import cn.hutool.core.date.DateTime;
import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CommunityMapper {
    List<Community> getCommunity();

    long getCommunityIdByName(String community_name);

    List<Map> getTotalHotCommunity();


    List<Item_Details> getCommunityDetails(long community_id, int pageNum, DateTime queryType);

}
