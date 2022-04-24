package com.lsk.smsbackend2.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lsk.smsbackend2.mapper.StudentMapper;
import com.lsk.smsbackend2.model.Student;
import com.lsk.smsbackend2.redis.RedisDao;
import com.lsk.smsbackend2.response.StatusCode;
import com.lsk.smsbackend2.service.StudentService;
import com.lsk.smsbackend2.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentHelper {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private StudentMapper studentMapper;

    public String studentNameOfPersonId(Integer id) {
        if (redisDao.has("STUDENT-NAME-" + id)) {
            return redisDao.get("STUDENT-NAME-" + id);
        } else {
            Student student = studentMapper.selectById(id);
            if (student == null) {
                throw new StatusCode(404, "Student not found");
            }
            putStudent(student);
            return student.getName();
        }
    }

    public void putStudent(Student student) {
        redisDao.set("STUDENT-NAME-" + student.getPersonID(), student.getName());
        redisDao.set("STUDENT-ID-" + student.getPersonID(), student.getId().toString());
    }

    public Integer currentStudentId() {
        if (!SecurityUtil.isStudent()) {
            return 0;
        }
        String username = SecurityUtil.currentUsername();
        Integer personID = redisDao.getInteger(username + "-ID");
        return redisDao.getInteger("STUDENT-ID-" + personID);
    }
}
