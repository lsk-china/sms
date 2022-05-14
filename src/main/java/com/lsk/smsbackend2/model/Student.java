package com.lsk.smsbackend2.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lsk.smsbackend2.excel.entity.ExcelStudent;
import com.lsk.smsbackend2.response.Bean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Bean
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("students")
public class Student {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String clazz;
    private Integer age;
    private Integer sex;
    private String image;
    @TableField("dormitoryID")
    private Integer dormitoryID;
    @TableField("matriculateNum")
    private Integer matriculateNum;
    private String address;
    private String telephone;
    @TableField("personID")
    private Integer personID;

    public static Student fromExcelStudent(ExcelStudent excelStudent) {
        Student student = new Student();
        student.setName(excelStudent.getName());
        student.setAge(excelStudent.getAge());
        student.setSex("男".equals(excelStudent.getSex()) ? 1 : 0);
        student.setTelephone(excelStudent.getTelephone());
        student.setAddress(excelStudent.getAddress());
        student.setMatriculateNum(excelStudent.getMatriculateNum());
        return student;
    }
}
