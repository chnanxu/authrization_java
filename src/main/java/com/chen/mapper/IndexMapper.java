package com.chen.mapper;


import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface IndexMapper {
    List<Item_Details> findIndex();

    List<Item_Details> getAnnounce(long community_id, LocalDate query_date);

    List<String> findUserItem();

    List<String> findCreateLeftItem();

    List<String> searchByText(String text);

    List<Item_Details> getSearchDetailsByKeywords(String keywords);

    List<Community> getHotCommunityBySort(String sort_type);

//    List<Community> getHotCommunityByWeek();
//
//    List<Community> getHotCommunityByMonth();
//
//    List<Community> getHotCommunityByQuarter();
//
//    List<Community> getHotCommunityByDay();
//
//    List<Community> getHotCommunityByDay();

}
