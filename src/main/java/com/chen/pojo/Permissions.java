package com.chen.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@Data
@JsonSerialize

@NoArgsConstructor
@AllArgsConstructor
public class Permissions implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority(){
        return this.authority;
    }

}
