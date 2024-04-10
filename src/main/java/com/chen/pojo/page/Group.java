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
    private String type_id;
    private String type_name;
    private String cover_img;
    private String href;
    private String create_time;
    private long total_times;
    private long day_times;
    private long week_times;
    private long month_times;
    private long threemonth_times;
    private long sixmonth_times;
    private long year_times;

    @Transient
    private boolean isUserLike;

    @Transient
    private String last_sign_time;

}
