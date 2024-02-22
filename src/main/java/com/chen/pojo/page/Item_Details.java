package com.chen.pojo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item_Details {
    private String uid;
    private String uname;
    private int pid;
    private String typeId;
    private String typename;
    private String title;
    private String content;
    private String img;
    private String href;
    private int readTimes;
    private int likeSize;
    private int commentsSize;
    private int favorites;
    private int dayTimes;
}
