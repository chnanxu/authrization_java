package com.chen.pojo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item_Comments {
    private long comment_id;
    private String content;
    private String createTime;
    private long likeTimes;
    private String uid;
    private String uname;
    private boolean isDelete;
    private long pid;
    private long root_commentID;
    private long to_commentID;
    private String updateTime;

    @Transient
    private int sonCommentCount;

    @Transient
    private List<Item_Comments> sonList;

    @Transient
    private boolean isUserLike;

}
