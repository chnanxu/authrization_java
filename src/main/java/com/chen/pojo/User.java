package com.chen.pojo;



import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@Data
public class User implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    @Transient
    private List<GrantedAuthority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String getUsername(){
        return username;
    }

    //账户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //账户是否锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //凭证是否过期，认证后的密码
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //用户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }

}
