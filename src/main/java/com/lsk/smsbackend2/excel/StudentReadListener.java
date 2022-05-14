package com.lsk.smsbackend2.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.lsk.smsbackend2.excel.entity.ExcelStudent;
import com.lsk.smsbackend2.model.Student;
import com.lsk.smsbackend2.service.StudentService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class StudentReadListener implements ReadListener{
    private final StudentService studentService;
//    private static final int BATCH = 100;
//    private final ArrayList<ExcelStudent> cache = new ArrayList<>();

    public void invoke(ExcelStudent excelStudent, AnalysisContext context) {
//        cache.add(excelStudent);
//        if (cache.size() > BATCH) {
//            studentService.admitMultiStudents(cache);
//            cache.clear();
//        }
        Student student = Student.fromExcelStudent(excelStudent);
        studentService.admitStudent(student.getName(), student.getAge(), student.getSex(), student.getMatriculateNum(), student.getAddress(), student.getTelephone());
    }


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        invoke(ExcelStudent.fromLinkedHashMap((LinkedHashMap<Integer, String>) o), analysisContext);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        studentService.admitMultiStudents(cache);
    }
}
