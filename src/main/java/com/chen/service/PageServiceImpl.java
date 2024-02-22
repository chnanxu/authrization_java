package com.chen.service;


import com.chen.mapper.PageMapper;
import com.chen.pojo.page.Item_Details;
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

    @Override
    public List<String> getGroup(){return pageMapper.getGroup();}

    @Override
    public Item_Details getPageDetails(String pid) {
        return pageMapper.getPageDetails(pid);
    }
}
