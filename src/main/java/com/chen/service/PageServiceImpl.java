package com.chen.service;


import com.chen.mapper.PageMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.UserInfo;
import com.chen.pojo.user.UserLikeComment;
import com.chen.utils.result.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService{

    private final PageMapper pageMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public PageServiceImpl(PageMapper pageMapper){
        this.pageMapper=pageMapper;
    }
    @Override
    public List<String> getHeaderItem() {
        return pageMapper.getHeaderItem();
    }

    @Override
    public List<String> getGroup(){return pageMapper.getGroup();}

    @Override
    public Item_Details getPageDetails(String pid) {

        pageMapper.addReadTimes(pid);

        return pageMapper.getPageDetails(pid);
    }

    @Override
    public List<Item_Comments> getPageDetailsComments(String pid) {

        List<Item_Comments> rootComments= pageMapper.getPageDetailsComments(pid);   //获取顶级评论列表

        for (Item_Comments item:rootComments   //获取顶级评论的前三条子评论列表
             ) {

            item.setSonList(pageMapper.getSonComments(item.getComment_id()));
        }

        return rootComments;
    }

    @Override
    public void submitComment(Item_Comments commentData) {

        pageMapper.submitComment(commentData);

    }

    @Override
    public void onLikeAdd(UserLikeComment userLikeComment) {


        userMapper.addUserComment(userLikeComment.getUid(), userLikeComment.getPid(), userLikeComment.getComment_id());

        pageMapper.onLikeAdd(userLikeComment.getComment_id());
    }

}
