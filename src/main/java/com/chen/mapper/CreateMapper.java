package com.chen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
@Repository
public interface CreateMapper {
    List<Map> getCommunityList();

    List<Map> getCommunityListByType(String type_name,int pageNum);
}
