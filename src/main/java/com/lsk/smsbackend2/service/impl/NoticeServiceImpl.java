package com.lsk.smsbackend2.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.mapper.NoticeMapper;
import com.lsk.smsbackend2.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Value("${paging.items-per-page}")
    private Integer itemsPerPage;

    @Override
    public Page<NoticeMapper.MixedNotice> allNotices(Integer page) {
        return noticeMapper.queryAllNotices(new Page<>(page, itemsPerPage));
    }

    @Override
    public void publishNotice(String title, String content) {

    }

    @Override
    public void publishItemNotice(String title, String content, String address, String receiveTime) {

    }

    @Override
    public void deleteNotice(Integer id) {

    }

    @Override
    public void receiveItem(Integer message) {

    }

    @Override
    public Page<String> notReceivedStudents(Integer id) {
        return null;
    }

    @Override
    public Boolean hasReceived(Integer id) {
        return null;
    }
}
