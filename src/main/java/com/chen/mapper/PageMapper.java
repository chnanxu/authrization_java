package com.chen.mapper;


import com.chen.pojo.page.All_Type;
import com.chen.pojo.page.Group;
import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.UserLikeComment;
import com.chen.pojo.user.UserLikeDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PageMapper {
    List<String> getHeaderItem();
    List<Group> getGroup();


    void addReadTimes(long pid);
    Item_Details getPageDetails(long pid);

    List<Item_Comments> getPageDetailsComments(long pid);

    List<Item_Comments> getSonComments(long comment_id);

    UserLikeComment getUserLikeComments(String uid,long pid,long comment_id);

    void submitComment(Item_Comments commentData);


    List<String> getLeftNavbar();

    String getReCommentUname(long to_commentID);

    void deleteComment(long commentId);

    List<All_Type> getTypeList();

    void updateItemCommentSize(long pid);

    List<Item_Comments> getAllSonComment(long comment_id);

    List<Group> getTotalHotCommunity();


}
