package com.lsk.sms.service;

import com.lsk.sms.model.Student;

import java.util.List;

public interface StudentService {
    public Student info();
    public void updateInfo(String name, String address, String telephone);
    public Student findStudentByMatriculateNum(Integer matriculateNum);

    void admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, Integer telephone);

    void createStudent(String name, String clazz, Integer age, Integer sex, Integer dormitoryID, Integer matriculateNum, String address, Integer telephone, Integer personID);

    void studentReport(String clazz, Integer dormitoryID, Integer personID, Integer matriculateNum);

    List<Student> notReportedStudents();

    void deleteStudent(Integer id);
}
