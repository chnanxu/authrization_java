package com.chen.pojo.page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private int gid;
    private String gname;
    private String type;
    private String href;
    private int times;

}
