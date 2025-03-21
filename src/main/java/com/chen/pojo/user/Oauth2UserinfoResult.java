package com.chen.pojo.user;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class Oauth2UserinfoResult {
    /**
     * 自增id
     */
    private String uid;

    /**
     * 用户账号
     */
    private String sub;

    /**
     * 用户名、昵称
     */
    private String uname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像地址
     */
    private String user_img;

    /**
     * 用户来源
     */
    private String sourceFrom;

    /**
     * 权限信息
     */
    private Collection<SimpleGrantedAuthority> authorities;

    /**
     * 地址
     */
    private String location;

    private long level;

    /**
     * 三方登录用户名
     */
    private String thirdUsername;

    /**
     * 三方登录获取的认证信息
     */
    private String credentials;

    /**
     * 三方登录获取的认证信息的过期时间
     */
    private LocalDateTime credentialsExpiresAt;
}
