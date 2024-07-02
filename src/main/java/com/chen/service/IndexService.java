package com.chen.service;


import com.chen.pojo.page.Item_Details;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IndexService {
    List<Item_Details> getIndex();

    Map<String,List<Item_Details>> getAnnouncement(String announcementCommunitySortType,String announcementSortType);

    List<Item_Details> getAnnouncementByCommunityName(String community_name,String announcementSortType);

    List<String> findUserItem();

    List<String> finCreateLeftItem();

    List<String> searchTempList(String text);

    List<Item_Details> getSearchDetails(String keywords);
}
