package com.chen.pojo;


import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity(name="users")
@Data
public class User implements UserDetails {

    @Id
    private String id;
    private String username;
    private String password;
    private boolean enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
