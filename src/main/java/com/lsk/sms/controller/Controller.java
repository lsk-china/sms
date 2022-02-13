package com.lsk.sms.controller;

import com.lsk.sms.model.Payment;
import com.lsk.sms.model.Student;
import com.lsk.sms.response.JsonReturn;
import com.lsk.sms.service.PaymentService;
import com.lsk.sms.service.PersonService;
import com.lsk.sms.service.StudentService;
import com.lsk.sms.util.HashUtil;
import com.lsk.sms.util.SecurityUtil;
import com.lsk.sms.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class Controller {

    @Autowired
    private PersonService personService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PaymentService paymentService;

    /**
     * 查询所有用户
     * @return
     */
    @JsonReturn
    @GetMapping("/admin/personList")
    public Object personList() {
        return personService.queryAllPersons();
    }

    /**
     * 是否已登录
     * @return
     */
    @JsonReturn
    @GetMapping("/loginInfo")
    public Object loginInfo () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityUtil.currentUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Map<String, Object> resp = new HashMap<>();
        resp.put("name", username);
        resp.put("authorities", authorities);
        return resp;
    }

    /**
     * 当前学生信息
     * @return
     */
    @JsonReturn
    @GetMapping("/student/info")
    public Object studentInfo() {
        return studentService.info();
    }

    /**
     * 更新当前学生信息
     */
    @JsonReturn
    @PostMapping("/student/updateInfo")
    public Object updateInfo(String name, String address, String telephone) {
        studentService.updateInfo(name, address, telephone);
        return "Success";
    }

    /**
     * 根据录取通知书编号查询学生
     * @param matriculate
     * @return
     */
    @JsonReturn
    @GetMapping("/admin/queryStudentByMatriculate")
    public Object queryStudentByMatriculate(Integer matriculate) {
        return studentService.findStudentByMatriculateNum(matriculate);
    }

    /**
     * 录取学生
     * @param name
     * @param age
     * @param sex
     * @param matriculateNum
     * @param address
     * @param telephone
     * @return
     */
    @JsonReturn
    @PostMapping("/admin/admitStudent")
    public Object admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, Integer telephone) {
        studentService.admitStudent(name, age, sex, matriculateNum, address, telephone);
        return "Success";
    }

    /**
     * 学生报道
     * @param matriculateNum
     * @param clazz
     * @param dormitoryID
     * @return
     */
    @JsonReturn
    @PostMapping("/admin/studentReport")
    public Object studentReport(Integer matriculateNum, String clazz, Integer dormitoryID) {
        Map<String, Object> result = new HashMap<>();
        String temporaryUsername = StringUtil.randomString(10);
        result.put("temporaryUsername", temporaryUsername);
        String temporaryPassword = StringUtil.randomString(10);
        result.put("temporaryPassword", temporaryPassword);
        Integer personID = personService.createStudent(temporaryUsername, temporaryPassword);
        studentService.studentReport(clazz, dormitoryID, personID, matriculateNum);
        return "Success";
    }

    /**
     * 学生缴费
     * @param targetPaymentID
     * @param serialNumber
     * @return
     */
    @JsonReturn
    @PostMapping("/student/pay")
    public Object studentPay(Integer targetPaymentID, Integer serialNumber) {
        paymentService.pay(targetPaymentID, serialNumber);
        return "Success";
    }

    /**
     * 更改当前用户密码
     * @param newPassword
     * @return
     */
    @JsonReturn
    @PostMapping("/changePassword")
    public Object changePassword(String newPassword) {
        personService.updatePassword(newPassword);
        return "Success";
    }

    /**
     * 更改当前用户昵称
     * @param newUsername
     * @return
     */
    @JsonReturn
    @GetMapping("/changeUsername")
    public Object changeUsername(String newUsername) {
        personService.updateUsername(newUsername);
        return "Success";
    }

    /**
     * 发布缴费信息
     * @param content
     * @param fee
     * @param limitDate
     * @return
     * @throws ParseException
     */
    @JsonReturn
    @PostMapping("/finance/publishPayment")
    public Object publishPayment(String content, Integer fee, String limitDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date limitDateObject = dateFormat.parse(limitDate);
        paymentService.publishPayment(content, limitDateObject, fee);
        return "Success";
    }

    /**
     * 创建用户
     * @param username
     * @param password
     * @param role
     * @return
     */
    @JsonReturn
    @PostMapping("/admin/createPerson")
    public Object createPerson(String username, String password, String role) {
        Integer personID = personService.createPerson(username, password);
        personService.grantPerson(personID, role);
        return "Success";
    }

    /**
     * 改变用户角色
     * @param targetID
     * @param role
     * @return
     */
    @JsonReturn
    @GetMapping("/admin/grant")
    public Object grant(Integer targetID, String role) {
        personService.grantPerson(targetID, role);
        return "Success";
    }

    /**
     * 查询所有缴费信息
     * @return
     */
    @JsonReturn
    @GetMapping("/payments")
    public Object payments() {
        return paymentService.allPayments();
    }

    /**
     * 查询我的缴费记录
     * @return
     */
    @JsonReturn
    @GetMapping("/student/myPayRecords")
    public Object myPayRecords() {
        return paymentService.myPayRecords();
    }

    /**
     * 查询没有缴费的学生
     * @param paymentID
     * @return
     */
    @JsonReturn
    @GetMapping("/finance/notPayedStudents")
    public Object notPayedStudents(Integer paymentID) {
        return paymentService.studentsNotPayed(paymentID);
    }

    /**
     * 查询我未报道的学生
     */
    @JsonReturn
    @GetMapping("/admin/notReportedStudents")
    public Object notReportedStudents() {
        return studentService.notReportedStudents();
    }
}
