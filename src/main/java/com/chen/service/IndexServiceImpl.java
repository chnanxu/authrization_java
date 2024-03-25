package com.chen.service;

import com.chen.mapper.IndexMapper;

import com.chen.pojo.page.Item_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    IndexMapper indexMapper;
    public List<Item_Details> getIndex(){
        List<Item_Details> item=indexMapper.findIndex();
        return item;
    }


    @Override
    public List<String> findUserItem() {
        return indexMapper.findUserItem();
    }

    @Override
    public List<String> finCreateLeftItem() {
        return indexMapper.findCreateLeftItem();
    }

}
