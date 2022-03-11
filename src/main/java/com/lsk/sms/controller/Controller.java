package com.lsk.sms.controller;

import com.lsk.sms.dao.NoticeDao;
import com.lsk.sms.response.annotation.FormatDate;
import com.lsk.sms.response.annotation.JsonReturn;
import com.lsk.sms.response.annotation.Pagination;
import com.lsk.sms.service.NoticeService;
import com.lsk.sms.service.PaymentService;
import com.lsk.sms.service.PersonService;
import com.lsk.sms.service.StudentService;
import com.lsk.sms.util.SecurityUtil;
import com.lsk.sms.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private NoticeService noticeService;

    /**
     * 查询所有用户
     * @return
     */
    @JsonReturn
    @Pagination
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
    public Object admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, String telephone) {
        studentService.admitStudent(name, age, sex, matriculateNum, address, telephone);
        return "Success";
    }

    /**
     * 学生报道
     *
     * @param clazz
     * @param dormitoryID
     * @return
     */
    @JsonReturn
    @PostMapping("/admin/studentReport")
    public Object studentReport(Integer id, String clazz, Integer dormitoryID) {
        Map<String, Object> result = new HashMap<>();
        String temporaryUsername = StringUtil.randomString(10);
        result.put("temporaryUsername", temporaryUsername);
        String temporaryPassword = StringUtil.randomString(10);
        result.put("temporaryPassword", temporaryPassword);
        Integer personID = personService.createStudent(temporaryUsername, temporaryPassword);
        studentService.studentReport(clazz, dormitoryID, personID, id);
        return result;
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
    @Pagination
    @FormatDate("yyyy MM dd HH mm ss")
    @GetMapping("/payments")
    public Object payments() {
        return paymentService.allPayments();
    }

    /**
     * 查询我的缴费记录
     * @return
     */
    @JsonReturn
    @Pagination
    @FormatDate("yyyy MM dd HH mm ss")
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
    @Pagination(5)
    @GetMapping("/finance/notPayedStudents")
    public Object notPayedStudents(Integer paymentID) {
        return paymentService.studentsNotPayed(paymentID);
    }

    /**
     * 查询我未报道的学生
     */
    @JsonReturn
    @Pagination
    @GetMapping("/admin/notReportedStudents")
    public Object notReportedStudents() {
        return studentService.notReportedStudents();
    }

    @JsonReturn
    @GetMapping("/admin/deleteStudent")
    public Object deleteStudent(Integer id) {
        studentService.deleteStudent(id);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/admin/deletePerson")
    public Object deletePerson(Integer id) {
        personService.deletePerson(id);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/finance/deletePayment")
    public Object deletePayment(Integer id) {
        paymentService.deletePayment(id);
        return "Success";
    }

    @JsonReturn
    @Pagination
    @GetMapping("/admin/allStudents")
    public Object allStudents() {
        return studentService.queryStudents();
    }

    @JsonReturn
    @PostMapping("/admin/notice/publish")
    public Object publishNotice(String title, String content) {
        noticeService.publishNotice(title, content);
        return "Success";
    }

    @JsonReturn
    @PostMapping("/finance/notice/publish")
    public Object publishItemNotice(String title, String content, String address, String receiveDate) {
        noticeService.publishItemNotice(title, content, address, receiveDate);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/student/notice/receive")
    public Object receiveItem(Integer noticeID) {
        noticeService.receiveItem(noticeID);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/finance/notice/notReceivedStudents")
    public Object notReceivedStudents(Integer noticeID) {
        return noticeService.notReceivedStudents(noticeID);
    }

    @JsonReturn
    @Pagination
    @FormatDate("yyyy MM dd HH mm ss")
    @GetMapping({"/student/notice/list", "/admin/notice/list", "/finance/notice/list"})
    public Object noticeList() {
        return noticeService.allNotices();
    }

    @JsonReturn
    @GetMapping("/admin/notice/delete")
    public Object deleteNotice(Integer noticeID) {
        noticeService.deleteNotice(noticeID);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/name")
    public Object username(Integer id) {
        return personService.name(id);
    }

    @JsonReturn
    @GetMapping("/student/notice/itemReceived")
    public Object itemReceived(Integer id) { return noticeService.hasReceived(id); }

}
