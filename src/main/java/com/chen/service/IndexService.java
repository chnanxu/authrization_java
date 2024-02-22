package com.chen.service;


import com.chen.pojo.page.Item_Details;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.List;

@Service
public interface IndexService {
    List<Item_Details> getIndex();


}
