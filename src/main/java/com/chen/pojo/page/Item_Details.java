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
    private long pid;
    private String typeId;
    private String typename;
    private String title;
    private String content;
    private String img;
    private String href;
    private long readTimes;
    private long likeSize;
    private long commentsSize;
    private long favorites;
    private long dayTimes;
}
