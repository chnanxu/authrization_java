package com.chen.pojo.page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item_Details_Temp {
    private String uid;
    private String uname;
    private String pid;
    private int type_id;
    private String type_name;
    private String title;
    private String content;
    private String cover_img;
    private String href;
}
