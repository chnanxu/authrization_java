package com.chen.service;


import com.chen.mapper.CommunityMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.community.Community_Details;
import com.chen.pojo.page.Community;
import com.chen.pojo.user.Oauth2UserinfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{

    private final CommunityMapper communityMapper;
    private final UserDetailService userDetailService;
    private final UserMapper userMapper;

    @Override
    public List<Community> getGroup(){

        List<Community> result=communityMapper.getGroup();
        Oauth2UserinfoResult userInfo=userDetailService.getLoginUserInfo();

        //用户是否登录
        if(userInfo!=null){

            for (Community item:result
            ) {
                if(userMapper.getUserLikeCommunity(userInfo.getUid(),item.getGid())!=null){
                    item.setUserLike(true);
                }
            }
        }

        return result;
    }

    @Override
    public Community_Details createProject(Community_Details item) {

        if(communityMapper.insertCommunity_Details(item)==0){

            return item;
        }else{
            return null;
        }
    }
}
