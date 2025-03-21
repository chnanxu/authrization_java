package com.chen.pojo.page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item_Details_Temp {
    private String uid;
    private String uname;
    private String pid;
    private long community_id;
    private String community_name;
    private int type_id;
    private String type_name;
    private String title;
    private String simple_text;
    private String content;
    private String cover_img;
    private String href;
    private int isOK;
    private String create_time;

    private String refuse_reason;
}
