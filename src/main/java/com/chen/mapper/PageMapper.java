package com.chen.mapper;


import com.chen.pojo.page.Item_Details;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PageMapper {
    List<String> getHeaderItem();
    List<String> getGroup();

    Item_Details getPageDetails(String pid);
}
