package com.chen.service;

import com.chen.pojo.page.All_Type;
import com.chen.pojo.page.Group;
import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.UserLikeComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {
    List<String> getHeaderItem();

    List<Group> getGroup(String token);

    Item_Details getPageDetails(long pid);

    List<Item_Comments> getPageDetailsComments(long pid,String token);

    void submitComment(Item_Comments commentData);

    void onLikeComment(UserLikeComment userLikeComment);


    List<String> getLeftNavbar();

    void submitReComment(Item_Comments commentData);

    String getReCommentUname(long to_commentID);

    void deleteComment(long commentId);

    List<All_Type> getTypeList();

    void onLikeDetails(String uid, long pid);
}
