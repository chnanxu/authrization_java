package com.chen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface Oauth2BasicUserMapper extends BaseMapper<User> {
}
