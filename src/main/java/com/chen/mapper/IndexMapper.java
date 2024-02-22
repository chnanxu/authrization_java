package com.chen.mapper;


import com.chen.pojo.page.Item_Details;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.List;

@Mapper
@Repository
public interface IndexMapper {
    List<Item_Details> findIndex();


}
