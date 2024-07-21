package com.chen.service;

import com.chen.mapper.CommunityMapper;
import com.chen.mapper.IndexMapper;

import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.Oauth2UserinfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final IndexMapper indexMapper;
    private final CommunityMapper communityMapper;
    private final UserDetailService userDetailService;
    private final UserService userService;
    public List<Item_Details> getIndex(){
        List<Item_Details> item=indexMapper.findIndex();
        return item;
    }

    @Override
    public Map<String,List<Item_Details>> getAnnouncement(String announcementCommunitySortType,String announcementSortType) {

        Oauth2UserinfoResult user=userDetailService.getLoginUserInfo();

        Map<String,List<Item_Details>> result=new HashMap<>();

        //作品查询按日期查询  日、周、月、年等
        LocalDate query_date=getQuery_date(announcementSortType);

        if(user.getUid()!=null){

            List<Community> guanzhuList=userService.getUserLikeCommunity(user.getUid());

            for (Community item:guanzhuList) {
               result.put(item.getCommunity_name(),indexMapper.getAnnounce(item.getCommunity_id(),query_date));
            }

        }else{

            List<Community> hotList=indexMapper.getHotCommunityBySort(announcementCommunitySortType);

            for(Community item:hotList){
                result.put(item.getCommunity_name(),indexMapper.getAnnounce(item.getCommunity_id(),query_date));
            }

        }

        return result;

    }

    @Override
    public List<Item_Details> getAnnouncementByCommunityName(String community_name,String announcementSortType) {



        LocalDate query_date=getQuery_date(announcementSortType);

        return indexMapper.getAnnounce(communityMapper.getCommunityIdByName(community_name),query_date);

    }

    public LocalDate getQuery_date(String announcementSortType){
        //作品查询按日期查询  日、周、月、年等
        LocalDate query_date=LocalDate.now();

        if(announcementSortType!=null){
            switch (announcementSortType){
                case "week":
                    String dayOfWeek=query_date.getDayOfWeek().toString();
                    switch (dayOfWeek){
                        case "MONDAY":
                            query_date=query_date.minusDays(0);
                            break;
                        case "TUESDAY":
                            query_date=query_date.minusDays(1);
                            break;
                        case "WEDNESDAY":
                            query_date=query_date.minusDays(2);
                            break;
                        case "THURSDAY":
                            query_date=query_date.minusDays(3);
                            break;
                        case "FRIDAY":
                            query_date=query_date.minusDays(4);
                            break;
                        case "SATURDAY":
                            query_date=query_date.minusDays(5);
                            break;
                        case "SUNDAY":
                            query_date=query_date.minusDays(6);
                            break;
                    }
                    break;
                case "month":
                    query_date=query_date.minusDays(query_date.getDayOfMonth()-1);
                    break;
                case "quarter":
                    query_date=query_date.minusMonths(2);
                    query_date=query_date.minusDays(query_date.getDayOfMonth()-1);
                    break;
                case "year":
                    query_date=query_date.minusMonths(query_date.getMonthValue()-1);
                    query_date=query_date.minusDays(query_date.getDayOfMonth()-1);
                    break;
                case "total":
                    query_date=query_date.minusYears(99);
                    query_date=query_date.minusMonths(query_date.getMonthValue()-1);
                    query_date=query_date.minusDays(query_date.getDayOfMonth()-1);
            }

        }else{

        }
        return query_date;
    }


    @Override
    public List<String> findUserItem() {
        return indexMapper.findUserItem();
    }

    @Override
    public List<String> finCreateLeftItem() {
        return indexMapper.findCreateLeftItem();
    }

    @Override
    public List<String> searchTempList(String text){


        return indexMapper.searchByText(text);
    }


    @Override
    public List<Item_Details> getSearchDetails(String keywords) {

        if(keywords==null){
            return indexMapper.findIndex();
        }else{
            return indexMapper.getSearchDetailsByKeywords(keywords);
        }

    }



}
