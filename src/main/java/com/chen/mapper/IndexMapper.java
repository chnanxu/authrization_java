package com.chen.mapper;

import com.chen.pojo.Index;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.List;

@Mapper
@Repository
public interface IndexMapper {
    List<Index> findMovies();

    List<Blob> getImg();
}
