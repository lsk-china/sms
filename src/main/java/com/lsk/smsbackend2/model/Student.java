package com.lsk.smsbackend2.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
