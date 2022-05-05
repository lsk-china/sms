package com.lsk.smsbackend2.excel;

import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.context.AnalysisContext;
import com.lsk.smsbackend2.excel.entity.ExcelStudent;
import com.lsk.smsbackend2.service.StudentService;
import org.springframework.stereotype.Component;
import org.springframework.bean.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StudentReadListener implements ReadListener<ExcelStudent> {
    @Autowired
    private StudentService studentService;

    @Override
    public void invoke(ExcelStudent excelStudent, AnalysisContent content) {
        String name = excelStudent.getName();
        Integer age = excelStudent.getAge();
        Integer sex = "男".equals(excelStudent.getSex()) ? 1 : 0;

    } 
}
