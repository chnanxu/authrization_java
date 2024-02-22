package com.chen.service;

import com.chen.pojo.page.Item_Details;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {
    List<String> getHeaderItem();

    List<String> getGroup();

    Item_Details getPageDetails(String pid);
}
