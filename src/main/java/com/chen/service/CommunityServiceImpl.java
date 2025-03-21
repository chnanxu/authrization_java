package com.chen.service;


import cn.hutool.core.date.DateTime;
import com.chen.mapper.CommunityMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.Oauth2UserinfoResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{

    private final CommunityMapper communityMapper;
    private final UserDetailService userDetailService;
    private final UserMapper userMapper;

    @Override
    public List<Community> getCommunity(){

        List<Community> result=communityMapper.getCommunity();
        Oauth2UserinfoResult userInfo=userDetailService.getLoginUserInfo();

        //用户是否登录
        if(userInfo!=null){

            for (Community item:result
            ) {
                if(userMapper.getUserLikeCommunity(userInfo.getUid(),item.getCommunity_id())!=null){
                    item.setUserLike(true);
                }
            }
        }

        return result;
    }

    @Override
    public List<Map> getTotalHotCommunity(){

        return communityMapper.getTotalHotCommunity();
    }



    @Override
    public List<Item_Details> getCommunityDetailsBySortType(long community_id, int pageNum, String sortType) {
        List<Item_Details> result;

        LocalDate queryTimeParameters=LocalDate.now();

        switch (sortType){

            case "week":{
                String dayOfWeek=queryTimeParameters.getDayOfWeek().toString();

                switch (dayOfWeek) {
                    case "MONDAY" -> queryTimeParameters=queryTimeParameters.minusDays(0);
                    case "TUESDAY" -> queryTimeParameters=queryTimeParameters.minusDays(1);
                    case "WEDNESDAY" -> queryTimeParameters=queryTimeParameters.minusDays(2);
                    case "THURSDAY" -> queryTimeParameters=queryTimeParameters.minusDays(3);
                    case "FRIDAY" -> queryTimeParameters=queryTimeParameters.minusDays(4);
                    case "SATURDAY" -> queryTimeParameters=queryTimeParameters.minusDays(5);
                    case "SUNDAY" -> queryTimeParameters=queryTimeParameters.minusDays(6);
                }

                break;
            }
            case "month":{
                queryTimeParameters=queryTimeParameters.minusDays(queryTimeParameters.getDayOfMonth()-1);
                break;
            }
            case "year":{
                queryTimeParameters=queryTimeParameters.minusMonths(queryTimeParameters.getMonthValue()-1);
                queryTimeParameters=queryTimeParameters.minusDays(queryTimeParameters.getDayOfMonth()-1);
                break;
            }
        }

        result= communityMapper.getCommunityDetails(community_id,pageNum*10-10,queryTimeParameters);
        return result;
    }
}
