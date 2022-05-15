package com.lsk.smsbackend2.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.excel.StudentReadListener;
import com.lsk.smsbackend2.model.User;
import com.lsk.smsbackend2.response.Response;
import com.lsk.smsbackend2.response.StatusCode;
import com.lsk.smsbackend2.service.NoticeService;
import com.lsk.smsbackend2.service.StudentService;
import com.lsk.smsbackend2.service.UserService;
import com.lsk.smsbackend2.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/personList")
    public Object personList (Integer page) {
        Page<User> result = userService.queryAllUser(page);
        return Response.ok(result);
    }

    @GetMapping("/queryStudentByMatriculate")
    public Object queryStudentByMatriculate(Integer matriculate) {
        return Response.ok(studentService.findStudentByMatriculateNum(matriculate));
    }

    @PostMapping("/admitStudent")
    public Object admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, String telephone) {
        studentService.admitStudent(name, age, sex, matriculateNum, address, telephone);
        return Response.ok("Success");
    }

    @PostMapping("/studentReport")
    public Object studentReport(Integer id, String clazz, Integer dormitoryID) {
        Map<String, Object> result = new HashMap<>();
        String temporaryUsername = StringUtil.randomString(10);
        result.put("temporaryUsername", temporaryUsername);
        String temporaryPassword = StringUtil.randomString(10);
        result.put("temporaryPassword", temporaryPassword);
        Integer personID = userService.createPerson(temporaryUsername, temporaryPassword, "STUDENT");
        studentService.studentReport(clazz, dormitoryID, personID, id);
        return Response.ok(result);
    }

    @PostMapping("/createPerson")
    public Object createPerson(String username, String password, String role) {
        userService.createPerson(username, password, role);
        return Response.ok("Success");
    }

    @GetMapping("/grant")
    public Object grant(Integer targetID, String role) {
        userService.grantPerson(targetID, role);
        return Response.ok("Success");
    }

    @GetMapping("/notReportedStudents")
    public Object notReportedStudents(Integer page) {
        return Response.ok(studentService.notReportedStudents(page));
    }

    @GetMapping("/deleteStudent")
    public Object deleteStudent(Integer id) {
        studentService.deleteStudent(id);
        return Response.ok("Success");
    }

    @GetMapping("/deletePerson")
    public Object deletePerson(Integer id) {
        userService.deletePerson(id);
        return Response.ok("Success");
    }

    @GetMapping("/allStudents")
    public Object allStudents(Integer page) {
        return Response.ok(studentService.queryStudents(page));
    }

    @PostMapping("/notice/publish")
    public Object publishItemNotice(String title, String content, String address, String receiveDate, String type) {
        if ("PLAIN".equals(type)) {
            noticeService.publishNotice(title, content);
        } else if ("ITEM".equals(type)) {
            noticeService.publishItemNotice(title, content, address, receiveDate);
        } else {
            return Response.error(new StatusCode(400, "Unknown type"));
        }
        return Response.ok("Success");
    }

    @GetMapping("/notice/delete")
    public Object deleteNotice(Integer noticeID) {
        noticeService.deleteNotice(noticeID);
        return Response.ok("Success");
    }
    @GetMapping("/notice/list")
    public Object noticeList(Integer page) {
        return Response.ok(noticeService.allNotices(page));
    }

    @PostMapping("/admitMultiStudents")
    public Object admitMultiStudents (MultipartFile file) throws IOException {
        log.debug(file.getOriginalFilename());
        if (!(file.getOriginalFilename().endsWith(".xls") || file.getOriginalFilename().endsWith(".xlsx"))) {
            return Response.error(new StatusCode(400, "Unsupported type"));
        }
        EasyExcel.read(file.getInputStream(), new StudentReadListener(studentService)).build().readAll();
        return Response.ok("Success");
    }
}
