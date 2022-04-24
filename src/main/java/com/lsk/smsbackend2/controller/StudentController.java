package com.lsk.smsbackend2.controller;

import com.lsk.smsbackend2.response.Response;
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
        return Response.ok(studentService.info());
    }

    @PostMapping("/student/updateInfo")
    public Object updateInfo(String name, String address, String telephone) {
        studentService.updateInfo(name, address, telephone);
        return Response.ok("Success");
    }

    @PostMapping("/student/pay")
    public Object studentPay(Integer targetPaymentID, Integer serialNumber) {
        paymentService.pay(targetPaymentID, serialNumber);
        return Response.ok("Success");
    }

    @GetMapping("/student/myPayRecords")
    public Object myPayRecords(Integer page) {
        return Response.ok(paymentService.myPayRecords(page));
    }

    @GetMapping("/student/notice/receive")
    public Object receiveItem(Integer noticeID) {
        noticeService.receiveItem(noticeID);
        return Response.ok("Success");
    }

    @GetMapping("/notice/list")
    public Object noticeList(Integer page) {
        return Response.ok(noticeService.allNotices(page));
    }

    @GetMapping("/student/notice/itemReceived")
    public Object itemReceived(Integer id) { return Response.ok(noticeService.hasReceived(id)); }
}
