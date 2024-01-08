package com.chen.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String uid;
    private String name;
    private char sex;
    private String email;
    private Date sign_time;
    private String user_img;
}
