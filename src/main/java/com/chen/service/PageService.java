package com.chen.service;

import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.UserLikeComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {
    List<String> getHeaderItem();

    List<String> getGroup();

    Item_Details getPageDetails(String pid);

    List<Item_Comments> getPageDetailsComments(String pid);

    void submitComment(Item_Comments commentData);

    void onLikeAdd(UserLikeComment userLikeComment);
}
