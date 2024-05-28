package com.chen.mapper;


import com.chen.pojo.Role;
import com.chen.pojo.User;
import com.chen.pojo.community.Community_Details;
import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    List<User> getUser(int pageNum);

    List<Role> getRole(int pageNum);

    List<Item_Details_Temp> getTempProject(int pageNum);

    Item_Details_Temp getTempProjectById(String uid,long pid);

    List<Item_Details> getProject(int pageNum);

    List<Item_Details> getDeletedProject(int pageNum);

    int reCoverProject(long pid);

    void setProject(Item_Details_Temp temp_item);

    int refuseProjectById(String uid,long pid);

    int deleteProjectById(long pid);

    List<Community> getCommunity();

    List<Community_Details> getCommunityDetails(long community_id);

    List<Community_Details> getTempCommunityDetails();
}
