package com.chen.service;


import cn.hutool.core.date.DateTime;
import com.chen.mapper.CommunityMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.community.Community_Details;
import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.Oauth2UserinfoResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    public Community_Details createProject(Community_Details item) {

        if(communityMapper.insertCommunity_Details(item)==0){

            return item;
        }else{
            return null;
        }
    }

    @Override
    public List<Item_Details> getCommunityDetailsBySortType(long community_id, int pageNum, String sortType) {
        List<Item_Details> result;

        SimpleDateFormat fd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime queryTimeParameters=DateTime.now();

        switch (sortType){
            case "day":{

            }
            case "week":{
                queryTimeParameters= DateTime.now();

            }
            case "month":{

            }
            case "year":{

            }
        }
        result= communityMapper.getCommunityDetails(community_id,pageNum*10-10,queryTimeParameters);
        return result;
    }
}
