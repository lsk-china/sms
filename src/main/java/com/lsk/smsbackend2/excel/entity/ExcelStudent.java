package com.lsk.smsbackend2.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;

@Slf4j
@Data
@AllArgsConstructor
public class ExcelStudent {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private String telephone;
    private String address;
    private Integer matriculateNum;

    public static ExcelStudent fromLinkedHashMap(LinkedHashMap<Integer, String> data) {
        log.debug(data.toString());
        Integer id = Integer.parseInt(data.get(0));
        String name = data.get(1);
        Integer age = Integer.parseInt(data.get(2));
        String sex = data.get(3);
        String telephone = data.get(4);
        String address = data.get(5);
        Integer matriculateNum = Integer.parseInt(data.get(6));
        return new ExcelStudent(id, name, age, sex, telephone, address, matriculateNum);
    }
}
