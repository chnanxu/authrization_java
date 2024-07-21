package com.chen.service;

import com.chen.pojo.page.All_Type;
import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.UserLikeComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {
    List<String> getHeaderItem();

    Item_Details getPageDetails(long pid);

    List<Item_Comments> getPageDetailsComments(long pid);

    List<Item_Comments> getAllSonComment(long pid,long comment_id);

    void submitComment(Item_Comments commentData);

    String onLikeComment(UserLikeComment userLikeComment);


    List<String> getLeftNavbar();

    void submitReComment(Item_Comments commentData);

    String getReCommentUname(long to_commentID);

    void deleteComment(long commentId);

    List<All_Type> getTypeList();

    String onLikeDetails(String uid, long pid);


}
