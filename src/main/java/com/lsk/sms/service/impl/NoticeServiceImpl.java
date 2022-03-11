package com.lsk.sms.service.impl;

import com.google.gson.Gson;
import com.lsk.sms.dao.NoticeDao;
import com.lsk.sms.dao.PersonDao;
import com.lsk.sms.dao.ReceiveLogDao;
import com.lsk.sms.dao.StudentDao;
import com.lsk.sms.model.Notice;
import com.lsk.sms.model.Person;
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
    @Autowired
    private PersonDao personDao;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy MM dd HH mm ss");

    @Override
    public List<Map<String, Object>> allNotices() {
        List<Notice> notices = noticeDao.queryAllNotices();
        return notices.stream()
                .map(notice -> {
                    Map<String, Object> resp = new HashMap<>();
                    resp.put("id", notice.getId());
                    resp.put("content", notice.getContent());
                    resp.put("title", notice.getTitle());
                    resp.put("type", notice.getType());
                    resp.put("publishDate", notice.getPublishDate());
                    Integer publisher = notice.getPublisher();
                    if (redisDao.has("PERSON-NAME-" + publisher)) {
                        resp.put("publisher", redisDao.get("PERSON-NAME-" + publisher));
                    } else {
                        Person person = personDao.queryPersonById(publisher);
                        redisDao.set("PERSON-NAME-" + publisher, person.getName());
                        resp.put("publisher", person.getName());
                    }
                    if (SecurityUtil.isStudent() && notice.getType().equals("ITEM")) {
                        Boolean hasReceived = hasReceived(notice.getId());
                        resp.put("hasReceived", hasReceived);
                    }
                    return resp;
                })
                .collect(Collectors.toList());
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
        noticeDao.addNotice(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        noticeDao.deleteNotice(id);
    }

    @Override
    public void receiveItem(Integer message) {
        String username = SecurityUtil.currentUsername();
        Integer studentID = Integer.parseInt(redisDao.get(username + "-STUDENTID"));
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
                .map(item -> redisDao.get("STUDENT-NAME-" + item))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public Boolean hasReceived(Integer id) {
        String username = SecurityUtil.currentUsername();
        Integer studentID = Integer.parseInt(redisDao.get(username + "-STUDENTID"));
        ReceiveLog receiveLog = receiveLogDao.queryReceiveLog(id, studentID);
        return receiveLog != null;
    }
}
