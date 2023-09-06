package com.chen.service;

import com.chen.mapper.MoviesMapper;
import com.chen.pojo.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesServiceImpl implements MoviesService{
    @Autowired
    MoviesMapper moviesMapper;
    public List<Movies> getMovies(){
        return moviesMapper.findMovies();
    }
}
