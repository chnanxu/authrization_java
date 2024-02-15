package com.chen.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {
    List<String> getHeaderItem();

    List<String> getGroup();
}
