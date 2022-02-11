package com.lsk.sms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private String clazz;
    private Integer age;
    private Integer sex;
    private String image;
    private Integer dormitoryID;
    private Integer matriculateNum;
    private String address;
    private Integer telephone;
    private Integer personID;
}
