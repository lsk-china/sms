package com.lsk.smsbackend2.controller;

import com.lsk.smsbackend2.service.NoticeService;
import com.lsk.smsbackend2.service.PaymentService;
import com.lsk.smsbackend2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/info")
    public Object studentInfo() {
        return studentService.info();
    }

    @PostMapping("/student/updateInfo")
    public Object updateInfo(String name, String address, String telephone) {
        studentService.updateInfo(name, address, telephone);
        return "Success";
    }

    @PostMapping("/student/pay")
    public Object studentPay(Integer targetPaymentID, Integer serialNumber) {
        paymentService.pay(targetPaymentID, serialNumber);
        return "Success";
    }

    @GetMapping("/student/myPayRecords")
    public Object myPayRecords(Integer page) {
        return paymentService.myPayRecords(page);
    }

    @GetMapping("/student/notice/receive")
    public Object receiveItem(Integer noticeID) {
        noticeService.receiveItem(noticeID);
        return "Success";
    }

    @GetMapping("/notice/list")
    public Object noticeList(Integer page) {
        return noticeService.allNotices(page);
    }

    @GetMapping("/student/notice/itemReceived")
    public Object itemReceived(Integer id) { return noticeService.hasReceived(id); }
}
