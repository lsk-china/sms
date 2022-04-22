package com.lsk.smsbackend2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.Student;

import java.util.List;

public interface StudentService {
    Student info();

    void updateInfo(String name, String address, String telephone);

    Student findStudentByMatriculateNum(Integer matriculateNum);

    void admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, String telephone);

    void createStudent(String name, String clazz, Integer age, Integer sex, Integer dormitoryID, Integer matriculateNum, String address, Integer telephone, Integer personID);

    void studentReport(String clazz, Integer dormitoryID, Integer personID, Integer id);

    Page<Student> notReportedStudents(Integer page);

    void deleteStudent(Integer id);

    Page<Student> queryStudents(Integer page);

    Page<Student> notPayedStudents(Integer page, Integer targetPaymentId);
}
