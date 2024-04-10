package com.chen.mapper;


import com.chen.pojo.User;
import com.chen.pojo.page.Group;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    List<User> getUser(int pageNum);
    List<Item_Details_Temp> getTempProject(int pageNum);

    Item_Details_Temp getTempProjectById(String uid,String pid);

    List<Item_Details> getProject(int pageNum);

    void setProject(Item_Details_Temp temp_item);

    List<Group> getCommunity();
}
