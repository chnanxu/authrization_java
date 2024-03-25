package com.chen.pojo.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommuninty {
    private String uid;
    private long gid;
    private String last_sign_time;

}
