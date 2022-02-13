package com.lsk.sms.service.impl;

import com.lsk.sms.dao.StudentDao;
import com.lsk.sms.model.Student;
import com.lsk.sms.redis.RedisDao;
import com.lsk.sms.response.StatusCode;
import com.lsk.sms.service.StudentService;
import com.lsk.sms.util.SecurityUtil;
import com.lsk.sms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public Student info() {
        String username = SecurityUtil.currentUsername();
        Integer id = Integer.parseInt(redisDao.get(username + "-ID"));
        return studentDao.queryStudentByPersonID(id);
    }

    @Override
    public void updateInfo(String name, String address, String telephone) {
        String username = SecurityUtil.currentUsername();
        Integer id = Integer.parseInt(redisDao.get(username + "-ID"));
        Student student = studentDao.queryStudentByPersonID(id);
        if (StringUtil.containsEmpty(name)) {
            name  = student.getName();
        }
        if (StringUtil.containsEmpty(address)) {
            address = student.getAddress();
        }
        if (StringUtil.containsEmpty(telephone)) {
            telephone = student.getTelephone().toString();
        }
        Integer telephoneNumber = Integer.valueOf(telephone);
        studentDao.updateStudent(name, address, telephoneNumber, id);
    }

    @Override
    public Student findStudentByMatriculateNum(Integer matriculateNum) {
        Student student = studentDao.queryStudentByMatriculateNum(matriculateNum);
        if (student == null) {
            throw new StatusCode(404, "No such student");
        }
        return student;
    }

    @Override
    public void admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, Integer telephone) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setSex(sex);
        student.setMatriculateNum(matriculateNum);
        student.setAddress(address);
        student.setTelephone(telephone);
        student.setDormitoryID(-1);
        student.setClazz("unset");
        student.setPersonID(-1);
    }

    @Override
    public void createStudent(String name, String clazz, Integer age, Integer sex, Integer dormitoryID, Integer matriculateNum, String address, Integer telephone, Integer personID) {

    }

//    @Override
//    public void createStudent(String name, String clazz, Integer age, Integer sex, Integer dormitoryID, Integer matriculateNum, String address, Integer telephone, Integer personID) {
//        Student student = new Student();
//        student.setName(name);
//        student.setClazz(clazz);
//        student.setAge(age);
//        student.setSex(sex);
//        student.setDormitoryID(dormitoryID);
//        student.setMatriculateNum(matriculateNum);
//        student.setAddress(address);
//        student.setTelephone(telephone);
//        student.setPersonID(personID);
//        studentDao.createStudent(student);
//    }

    @Override
    public void studentReport(String clazz, Integer dormitoryID, Integer personID, Integer matriculateNum) {
        studentDao.completeStudent(clazz, dormitoryID, personID, matriculateNum);
    }
    @Override
    public List<Student> notReportedStudents() {
        return studentDao.notReportedStudents();
    }
}
