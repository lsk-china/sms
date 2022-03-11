package com.lsk.sms.service;

import com.lsk.sms.model.Notice;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface NoticeService {
    List<Map<String, Object>> allNotices();
    void publishNotice(String title, String content);
    void publishItemNotice(String title, String content, String address, String receiveTime);
    void deleteNotice(Integer id);
    void receiveItem(Integer message);
    List<String> notReceivedStudents(Integer id);
    Boolean hasReceived(Integer id);
}
