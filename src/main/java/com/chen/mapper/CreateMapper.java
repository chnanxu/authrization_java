package com.chen.mapper;

import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CreateMapper {
    List<Map> getCommunityList();

    List<Map> getCommunityListByType(String type_name,int pageNum);

    int createNewProject(Item_Details_Temp temp_item);  //新建作品

    List<Item_Details> getMyProjectByTime(String uid);    //获取我的作品按时间排序

    List<Item_Details> getMyProjectByHot(String uid);    //获取我的作品按热度排序

    List<Item_Details> getMyProjectByNoAgree(String uid);    //获取我的待审核作品
}
