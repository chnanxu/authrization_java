package com.chen.mapper;


import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PageMapper {
    List<String> getHeaderItem();
    List<String> getGroup();


    void addReadTimes(String pid);
    Item_Details getPageDetails(String pid);

    List<Item_Comments> getPageDetailsComments(String pid);

    List<Item_Comments> getSonComments(long commentId);

    void submitComment(Item_Comments commentData);

    void onLikeAdd(long comment_id);
}
