package com.lsk.sms.service;

import com.lsk.sms.model.Notice;

import java.util.Date;
import java.util.List;

public interface NoticeService {
    List<Notice> allNotices();
    void publishNotice(String title, String content);
    void publishItemNotice(String title, String content, String address, Date receiveTime);
    void deleteNotice(Integer id);
    void receiveItem(Integer message);
    List<String> notReceivedStudents(Integer id);
}
