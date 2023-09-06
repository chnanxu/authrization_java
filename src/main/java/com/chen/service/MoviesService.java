package com.chen.service;

import com.chen.pojo.Movies;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MoviesService {
    List<Movies> getMovies();
}
