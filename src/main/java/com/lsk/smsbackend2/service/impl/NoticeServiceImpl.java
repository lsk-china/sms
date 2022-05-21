package com.lsk.smsbackend2.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.lsk.smsbackend2.helper.StudentHelper;
import com.lsk.smsbackend2.mapper.NoticeMapper;
import com.lsk.smsbackend2.mapper.ReceiveLogMapper;
import com.lsk.smsbackend2.model.Notice;
import com.lsk.smsbackend2.model.ReceiveLog;
import com.lsk.smsbackend2.redis.RedisDao;
import com.lsk.smsbackend2.service.NoticeService;
import com.lsk.smsbackend2.util.ReflectionUtil;
import com.lsk.smsbackend2.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private StudentHelper studentHelper;

    @Autowired
    private ReceiveLogMapper receiveLogMapper;

    @Autowired
    private RedisDao redisDao;

    @Value("${paging.items-per-page}")
    private Integer itemsPerPage;

    private static final Gson gson = new Gson();

    @Override
    public Page<Map<String, Object>> allNotices(Integer page) {
        Page<NoticeMapper.MixedNotice> notices = noticeMapper.queryAllNotices(new Page<>(page, itemsPerPage));
        Integer currentStudentId = studentHelper.currentStudentId();
        List<Map<String, Object>> result = notices.getRecords().stream()
                .map(e -> {
                    Map<String, Object> notice = ReflectionUtil.objectToMap(e);
                    if (e.getType().equals("ITEM") && SecurityUtil.isStudent()) {
                        notice.put("hasReceived", noticeMapper.hasReceived(currentStudentId, e.getId()));
                    }
                    return notice;
                }).collect(Collectors.toList());
        Page<Map<String, Object>> resultPage = new Page();
        resultPage.setTotal(notices.getTotal());
        resultPage.setCurrent(notices.getCurrent());
        resultPage.setSize(notices.getSize());
        resultPage.setRecords(result);
        return resultPage;
    }

    @Override
    public void publishNotice(String title, String content) {
        String username = SecurityUtil.currentUsername();
        Integer uid = Integer.parseInt(redisDao.get(username + "-ID"));
        Notice notice = new Notice();
        notice.setContent(content);
        notice.setTitle(title);
        notice.setPublisher(uid);
        notice.setPublishDate(new Date());
        notice.setType("PLAIN");
        noticeMapper.insert(notice);
    }

    @Override
    public void publishItemNotice(String title, String content, String address, String receiveTime) {
        Map<String, String> realContent = new HashMap<>();
        realContent.put("content", content);
        realContent.put("address", address);
        realContent.put("time", receiveTime);
        String realContentJson = gson.toJson(realContent);
        String username = SecurityUtil.currentUsername();
        Integer uid = Integer.parseInt(redisDao.get(username + "-ID"));
        Notice notice = new Notice();
        notice.setType("ITEM");
        notice.setContent(realContentJson);
        notice.setPublisher(uid);
        notice.setPublishDate(new Date());
        notice.setTitle(title);
        noticeMapper.insert(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        noticeMapper.deleteById(id);
    }

    @Override
    public void receiveItem(Integer message) {
        Integer studentID = studentHelper.currentStudentId();
        ReceiveLog receiveLog = new ReceiveLog();
        receiveLog.setStudentId(studentID);
        receiveLog.setReceiveDate(new Date());
        receiveLog.setNoticeId(message);
        receiveLogMapper.insert(receiveLog);
    }

    @Override
    public Page<String> notReceivedStudents(Integer id, Integer page) {
        return receiveLogMapper.notReceivedStudents(new Page(page, itemsPerPage), id);
    }

    @Override
    public Boolean hasReceived(Integer id) {
        Integer studentId = studentHelper.currentStudentId();
        return noticeMapper.hasReceived(studentId, id);
    }

}
