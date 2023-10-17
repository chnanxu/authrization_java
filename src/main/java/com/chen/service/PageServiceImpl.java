package com.chen.service;


import com.chen.mapper.PageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService{

    private final PageMapper pageMapper;

    @Autowired
    public PageServiceImpl(PageMapper pageMapper){
        this.pageMapper=pageMapper;
    }
    @Override
    public List<String> getHeaderItem() {
        return pageMapper.getHeaderItem();
    }
}
