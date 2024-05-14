package com.chen.mapper;


import com.chen.pojo.page.Item_Details;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexMapper {
    List<Item_Details> findIndex();

    List<String> findUserItem();

    List<String> findCreateLeftItem();

    List<String> searchByText(String text);

    List<Item_Details> getSearchDetailsByKeywords(String keywords);

}
