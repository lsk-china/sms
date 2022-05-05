package com.lsk.smsbackend2.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.lsk.smsbackend2.excel.entity.ExcelStudent;
import com.lsk.smsbackend2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Component
public class StudentReadListener implements ReadListener<ExcelStudent> {
    @Autowired
    private StudentService studentService;

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
        log.error("Parse Excel failed!", e);
        throw e;
    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {

    }

    @Override
    public void invoke(ExcelStudent excelStudent, AnalysisContext context) {
        String name = excelStudent.getName();
        Integer age = excelStudent.getAge();
        Integer sex = "男".equals(excelStudent.getSex()) ? 1 : 0;
        String telephone = excelStudent.getTelephone();
        String address = excelStudent.getAddress();
        Integer matriculateNum = excelStudent.getMatriculateNum();
        studentService.admitStudent(name, age, sex, matriculateNum, address, telephone);
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return false;
    }

}
