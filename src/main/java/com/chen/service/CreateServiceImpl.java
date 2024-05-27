package com.chen.service;

import com.chen.mapper.CreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateServiceImpl implements CreateService{

    private final CreateMapper createMapper;

    @Override
    public List<Map> getCommunityListByQueryType(String queryType,int pageNum) {

        List<Map> result;
        if(queryType.equals("first")){

            result=createMapper.getCommunityList();
        }else{
            result=createMapper.getCommunityListByType(queryType,pageNum);
        }

        return result;
    }
}
