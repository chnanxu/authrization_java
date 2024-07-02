package com.chen.pojo;



import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.pojo.user.CustomGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID=1L;

    private String uid;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String uname;
    private byte sex;
    private String user_img;
    private String sign_time;
    private byte deleted;
    private String sourceFrom;
    private boolean enabled;
    private String location;

    @Transient
    private String captcha;

    @Transient
    private Collection<CustomGrantedAuthority> authorities;

    @Override
    public Collection<CustomGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled(){
        return this.deleted != 1;
    }
}
