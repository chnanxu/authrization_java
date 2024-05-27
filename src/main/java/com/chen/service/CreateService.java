package com.chen.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface CreateService {
    List<Map> getCommunityListByQueryType(String queryType, int pageNum);
}
