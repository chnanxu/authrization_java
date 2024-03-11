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
    private String type_id;
    private String type_name;
    private String title;
    private String content;
    private String cover_img;
    private String href;
    private long read_times;
    private long like_times;
    private long comments_times;
    private long favorites;
    private long day_times;
}
