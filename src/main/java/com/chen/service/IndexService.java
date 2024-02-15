package com.chen.service;

import com.chen.pojo.Index;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.List;

@Service
public interface IndexService {
    List<Index> getMovies(int start,int end);


    List<Blob> getImg();
}
