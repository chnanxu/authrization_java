package com.chen.pojo.community;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Community_Details {
    private long citem_details_id;
    private String uid;
    private String uname;
    private long community_id;
    private String title;
    private String content;
    private long like_times;
    private String href;
    private byte isDeleted;
    private long root_communityID;
    private long to_communityID;
    private String update_time;


    @Transient
    private boolean isOK;

}
