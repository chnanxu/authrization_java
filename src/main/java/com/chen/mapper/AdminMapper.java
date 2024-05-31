package com.chen.mapper;


import com.chen.pojo.Role;
import com.chen.pojo.User;
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

    List<Item_Details_Temp> getTempProjectList(int pageNum);

    Item_Details_Temp getTempProjectById(String uid,long pid);

    List<Item_Details> getProjectList(int pageNum);

    List<Item_Details> getDeletedProjectList(int pageNum);

    int reCoverProject(long pid);

    int setProject(Item_Details_Temp temp_item);

    int refuseProjectById(String uid,long pid);

    int deleteProjectById(long pid);

    int takeoffProjectById(long pid);

    List<Community> getCommunity();

    List<Item_Details> getTakeoffProjectList(int pageNum);
}
