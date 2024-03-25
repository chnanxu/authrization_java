package com.chen.pojo.page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private int gid;
    private String group_name;
    private String type;
    private String cover_img;
    private String href;
    private int total_times;

    @Transient
    private boolean isUserLike;

}
