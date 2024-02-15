package com.chen.service;

import com.chen.mapper.IndexMapper;
import com.chen.pojo.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    IndexMapper indexMapper;
    public List<Index> getMovies(int start,int end){
        return indexMapper.findMovies(start,end);
    }



    public List<Blob> getImg(){
        return indexMapper.getImg();
    }
}
