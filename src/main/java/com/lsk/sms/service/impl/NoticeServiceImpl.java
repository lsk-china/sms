package com.lsk.sms.service.impl;

import com.google.gson.Gson;
import com.lsk.sms.dao.NoticeDao;
import com.lsk.sms.dao.ReceiveLogDao;
import com.lsk.sms.dao.StudentDao;
import com.lsk.sms.model.Notice;
import com.lsk.sms.model.ReceiveLog;
import com.lsk.sms.redis.RedisDao;
import com.lsk.sms.redis.StudentCache;
import com.lsk.sms.service.NoticeService;
import com.lsk.sms.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private Gson gson;
    @Autowired
    private ReceiveLogDao receiveLogDao;
    @Autowired
    private StudentDao studentDao;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy MM dd HH mm ss");

    @Override
    public List<Notice> allNotices() {
        return noticeDao.queryAllNotices();
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
        noticeDao.addNotice(notice);
    }

    @Override
    public void publishItemNotice(String title, String content, String address, Date receiveTime) {
        Map<String, String> realContent = new HashMap<>();
        realContent.put("content", content);
        realContent.put("address", address);
        realContent.put("time", DATE_FORMAT.format(receiveTime));
        String realContentJson = gson.toJson(realContent);
        String username = SecurityUtil.currentUsername();
        Integer uid = Integer.parseInt(redisDao.get(username + "-ID"));
        Notice notice = new Notice();
        notice.setType("ITEM");
        notice.setContent(realContentJson);
        notice.setPublisher(uid);
        notice.setPublishDate(new Date());
        notice.setTitle(title);
        noticeDao.addNotice(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        noticeDao.deleteNotice(id);
    }

    @Override
    public void receiveItem(Integer message) {
        String username = SecurityUtil.currentUsername();
        Integer studentID = StudentCache.studentID(username);
        ReceiveLog receiveLog = new ReceiveLog();
        receiveLog.setStudentId(studentID);
        receiveLog.setReceiveDate(new Date());
        receiveLog.setNoticeId(message);
        receiveLogDao.addReceiveLog(receiveLog);
    }

    @Override
    public List<String> notReceivedStudents(Integer id) {
        List<Integer> receivedIDs = receiveLogDao.queryStudentIDs(id);
        List<Integer> studentIDs = studentDao.queryStudentIDs();
        List<String> result = studentIDs.stream()
                .filter(item -> ! receivedIDs.contains(item))
                .map(StudentCache::studentName)
                .collect(Collectors.toList());
        return result;
    }
}
