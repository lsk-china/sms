package com.lsk.sms.redis;

import com.lsk.sms.dao.StudentDao;
import com.lsk.sms.model.Student;
import com.lsk.sms.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StudentCache implements ApplicationRunner {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private StudentDao studentDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Start to build student name cache....");
        List<Student> students = studentDao.queryAllStudents();
        for (Student student : students) {
            redisDao.set("STUDENT-NAME-" + student.getId(), student.getName());
            redisDao.set("STUDENT-ID-" + student.getName(), student.getId().toString());
        }
        log.info("Build finished");
    }

    public static void addMapping(Integer id) {
        RedisDao redisDao = SpringUtil.getBean(RedisDao.class);
        StudentDao studentDao = SpringUtil.getBean(StudentDao.class);
        Student student = studentDao.queryStudentById(id);
        redisDao.set("STUDENT-NAME-" + student.getId(), student.getName());
        redisDao.set("STUDENT-ID-" + student.getName(), student.getId().toString());
    }

    public static Integer studentID(String personName) {
        RedisDao redisDao = SpringUtil.getBean(RedisDao.class);
        return Integer.parseInt(redisDao.get(personName + "-STUDENTID"));
    }

    public static Integer studentIDByStudentName(String studentName) {
        RedisDao redisDao = SpringUtil.getBean(RedisDao.class);
        return Integer.parseInt(redisDao.get("STUDENT-ID-" + studentName));
    }

    public static String studentName(Integer id) {
        RedisDao redisDao = SpringUtil.getBean(RedisDao.class);
        return redisDao.get("STUDENT-NAME-" + id);
    }
}
