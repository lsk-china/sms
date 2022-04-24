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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/finance/")
public class FinanceController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/publishPayment")
    public Object publishPayment(String content, Integer fee, String limitDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date limitDateObject = dateFormat.parse(limitDate);
        paymentService.publishPayment(content, limitDateObject, fee);
        return Response.ok("Success");
    }

    @GetMapping("/notPayedStudents")
    public Object notPayedStudents(Integer paymentID, Integer page) {
        return Response.ok(studentService.notPayedStudents(page, paymentID));
    }

    @GetMapping("/deletePayment")
    public Object deletePayment(Integer id) {
        paymentService.deletePayment(id);
        return Response.ok("Success");
    }

    @PostMapping("/notice/publish")
    public Object publishItemNotice(String title, String content, String address, String receiveDate) {
        noticeService.publishItemNotice(title, content, address, receiveDate);
        return Response.ok("Success");
    }

    @GetMapping("/notice/notReceivedStudents")
    public Object notReceivedStudents(Integer noticeID, Integer page) {
        return Response.ok(noticeService.notReceivedStudents(noticeID, page));
    }

    @GetMapping("/notice/list")
    public Object noticeList(Integer page) {
        return Response.ok(noticeService.allNotices(page));
    }
}
