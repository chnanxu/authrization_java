package com.chen.service;

import com.chen.mapper.AdminMapper;
import com.chen.pojo.community.Community;
import com.chen.pojo.page.Item_Details;
import com.chen.pojo.page.Item_Details_Temp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminMapper adminMapper;

    @Override
    public String refuseProject(String uid, long pid,String refuse_reason) {
        if(adminMapper.refuseProjectById(uid,pid,refuse_reason)==1){
            return "操作成功";
        }else{
            return "操作失败";
        }
    }

    @Override
    public String agreeProject(String uid, long pid) {
        Item_Details_Temp temp_item=adminMapper.getTempProjectById(uid,pid);

        if(temp_item==null){
            return "作品不存在";
        }

        temp_item.setHref("/page/details/"+temp_item.getType_id()+"/"+pid);
        int result=adminMapper.setProject(temp_item);

        if(result==1){
            return "审核通过";
        }else{
            return "异常,请联系管理员";
        }


    }

    @Override
    public int takeoffProject(long pid) {
        return adminMapper.takeoffProjectById(pid);
    }

    @Override
    public List<Item_Details_Temp> getTempProject(int pageNum) {
        return adminMapper.getTempProjectList(pageNum*10-10);
    }

    @Override
    public List<Item_Details> getProject(int pageNum) {
        return adminMapper.getProjectList(pageNum*10-10);
    }

    @Override
    public List<Item_Details> getDeletedProject(int pageNum) {
        return adminMapper.getDeletedProjectList(pageNum*10-10);
    }

    @Override
    public List<Item_Details> getTakeoffProject(int pageNum) {
        return adminMapper.getTakeoffProjectList(pageNum*10-10);
    }

    @Override
    public String createCommunity(Community community) {

        System.out.println(community.getCover_file());

        return null;
    }


}
