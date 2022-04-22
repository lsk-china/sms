package com.lsk.smsbackend2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.mapper.NoticeMapper;

public interface NoticeService {
    Page<NoticeMapper.MixedNotice> allNotices(Integer page);
    void publishNotice(String title, String content);
    void publishItemNotice(String title, String content, String address, String receiveTime);
    void deleteNotice(Integer id);
    void receiveItem(Integer message);
    Page<String> notReceivedStudents(Integer id);
    Boolean hasReceived(Integer id);
}
