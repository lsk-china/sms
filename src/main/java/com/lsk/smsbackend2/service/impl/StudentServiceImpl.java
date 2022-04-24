package com.lsk.smsbackend2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.mapper.StudentMapper;
import com.lsk.smsbackend2.model.Student;
import com.lsk.smsbackend2.redis.RedisDao;
import com.lsk.smsbackend2.service.StudentService;
import com.lsk.smsbackend2.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Value("${paging.items-per-page}")
    private Integer itemsPerPage;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisDao redisDao;

    @Override
    public Student info() {
        String username = SecurityUtil.currentUsername();
        Integer id = redisDao.getInteger(username + "-ID");
        return studentMapper.selectOne(new QueryWrapper<Student>().eq("personID", id));
    }

    @Override
    public void updateInfo(String name, String address, String telephone) {
        String username = SecurityUtil.currentUsername();
        Integer id = redisDao.getInteger(username + "-ID");
        Student student = new Student();
        student.setName(name.equals("") ? null : name);
        student.setAddress(address.equals("") ? null: address);
        student.setTelephone(telephone.equals("") ? null : telephone);
        studentMapper.update(student, new QueryWrapper<Student>().eq("personID", id));
    }

    @Override
    public Student findStudentByMatriculateNum(Integer matriculateNum) {
        return studentMapper.selectOne(new QueryWrapper<Student>().eq("matriculateNum", matriculateNum));
    }

    @Override
    public void admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, String telephone) {
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
        studentMapper.insert(student);
    }

    @Override
    public void createStudent(String name, String clazz, Integer age, Integer sex, Integer dormitoryID, Integer matriculateNum, String address, Integer telephone, Integer personID) {

    }

    @Override
    public void studentReport(String clazz, Integer dormitoryID, Integer personID, Integer id) {
        Student student = new Student();
        student.setId(id);
        student.setDormitoryID(dormitoryID);
        student.setPersonID(personID);
        studentMapper.updateById(student);
    }

    @Override
    public Page<Student> notReportedStudents(Integer page) {
        return studentMapper.selectPage(
                new Page<Student>(page, itemsPerPage),
                new QueryWrapper<Student>().eq("clazz", "unset")
        );
    }

    @Override
    public void deleteStudent(Integer id) {
        studentMapper.deleteById(id);
    }

    @Override
    public Page<Student> queryStudents(Integer page) {
        return studentMapper.selectPage(new Page<Student>(page, itemsPerPage), null);
    }

    @Override
    public Page<Student> notPayedStudents(Integer page, Integer targetPaymentId) {
        return studentMapper.queryNotPayedStudents(new Page<>(page, itemsPerPage), targetPaymentId);
    }
}
