package com.lsk.smsbackend2.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelStudent {
    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("电话")
    private String telephone;

    @ExcelProperty("住址")
    private String address;

    @ExcelProperty("录取通知书编号")
    private Integer matriculateNum;
}
