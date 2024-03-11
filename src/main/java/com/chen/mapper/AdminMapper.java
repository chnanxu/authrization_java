package com.chen.mapper;


import com.chen.pojo.page.Item_Details_Temp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    List<Item_Details_Temp> getTempProject();

    Item_Details_Temp getTempProjectById(String uid,String pid);


    void setProject(Item_Details_Temp temp_item);
}
