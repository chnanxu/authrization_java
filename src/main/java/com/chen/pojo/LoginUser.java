package com.chen.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private User user;

    private List<String> permissions;


    public LoginUser(User user, List<String> permissions) {
        this.user=user;
        this.permissions=permissions;
    }

    @JSONField(serialize = false)//该设置会将该变量忽略，不会存储到redis当中
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        if(authorities!=null){
            return authorities;
        }
        authorities=permissions
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    //账户是否过期  true为可用  false为过期不可用
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //账户是否锁定  true为可用  false为锁定不可用
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //凭证是否过期，认证后的密码   true为可用  false为凭证过期不可用
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //用户是否可用  true为可用  false为不可用
    @Override
    public boolean isEnabled() {
        return true;
    }

}
