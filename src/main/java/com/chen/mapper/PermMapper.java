package com.chen.mapper;


import com.chen.pojo.Authorities;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.security.Permission;
import java.util.List;

@Mapper
@Repository
public interface PermMapper {
    List<Authorities> getAuthority(String username);
}
