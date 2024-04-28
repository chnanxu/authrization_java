package com.chen.service;


import com.chen.pojo.page.Item_Details;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IndexService {
    List<Item_Details> getIndex();

    List<String> findUserItem();

    List<String> finCreateLeftItem();

    List<String> searchTempList(String text);
}
