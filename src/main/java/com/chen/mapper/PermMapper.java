package com.chen.mapper;


import com.chen.pojo.Permissions;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermMapper {
    List<Permissions> getAuthority(String username);
}
