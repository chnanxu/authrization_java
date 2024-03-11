package com.chen.service;


import com.chen.mapper.PageMapper;
import com.chen.mapper.UserMapper;
import com.chen.pojo.page.All_Type;
import com.chen.pojo.page.Item_Comments;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.user.UserInfo;
import com.chen.pojo.user.UserLikeComment;
import com.chen.utils.util.RedisCache;
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
    public List<All_Type> getTypeList() {
        return pageMapper.getTypeList();
    }

    @Override
    public List<String> getLeftNavbar() {
        return pageMapper.getLeftNavbar();
    }

    @Override
    public String getReCommentUname(long to_commentID) {
        return pageMapper.getReCommentUname(to_commentID);
    }

    @Override
    public void deleteComment(long commentId) {
        pageMapper.deleteComment(commentId);
    }



    @Override
    public List<String> getGroup(){return pageMapper.getGroup();}

    @Override
    public Item_Details getPageDetails(long pid) {

        pageMapper.addReadTimes(pid);


        return pageMapper.getPageDetails(pid);
    }

    @Override
    public List<Item_Comments> getPageDetailsComments(long pid) {

        List<Item_Comments> rootComments= pageMapper.getPageDetailsComments(pid);   //获取顶级评论列表

        UserInfo userInfo=redisCache.getCacheObject("userInfo:");


        for (Item_Comments commentItem:rootComments   //获取顶级评论的前三条子评论列表
        ) {
            if(pageMapper.getUserLikeComments(userInfo.getUid(),pid,commentItem.getComment_id())!=null){    //用户是否点赞
                commentItem.setUserLike(true);
            }
            commentItem.setSonList(pageMapper.getSonComments(commentItem.getComment_id()));
            for (Item_Comments sonCommentItem:commentItem.getSonList()
                 ) {
                if(pageMapper.getUserLikeComments(userInfo.getUid(),pid,sonCommentItem.getComment_id())!=null){  //用户是否点赞
                    sonCommentItem.setUserLike(true);
                }
            }
        }

        return rootComments;
    }

    @Override
    public void submitComment(Item_Comments commentData) {

        pageMapper.submitComment(commentData);

        pageMapper.updateItemCommentSize(commentData.getPid());

    }

    @Override
    public void submitReComment(Item_Comments commentData) {
        pageMapper.submitComment(commentData);
        pageMapper.updateItemCommentSize(commentData.getPid());
    }



    @Override
    public void onLikeAdd(UserLikeComment userLikeComment) {

        if(pageMapper.getUserLikeComments(userLikeComment.getUid(),userLikeComment.getPid(),userLikeComment.getComment_id())!=null){
            userMapper.deleteUserLikeComment(userLikeComment.getUid(), userLikeComment.getPid(), userLikeComment.getComment_id());
            pageMapper.onLikeDelete(userLikeComment.getComment_id());
        }else{
            userMapper.addUserLikeComment(userLikeComment.getUid(), userLikeComment.getPid(), userLikeComment.getComment_id());
            pageMapper.onLikeAdd(userLikeComment.getComment_id());
        }

    }



}
