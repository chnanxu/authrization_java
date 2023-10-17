package com.chen.pojo.admin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryUserResult {
    private String uid;
    private String username;
    private String password;
    private boolean enabled;
    private String role;

}
