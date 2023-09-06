package com.chen.mapper;

import com.chen.pojo.Movies;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MoviesMapper {
    List<Movies> findMovies();
}
