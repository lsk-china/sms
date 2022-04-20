package com.lsk.smsbackend2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.PayRecord;
import com.lsk.smsbackend2.model.Payment;
import com.lsk.smsbackend2.model.Student;

import java.util.Date;

public interface PaymentService {
    Page<Payment> allPayments(Integer page);

    Page<PayRecord> myPayRecords(Integer page);

    void pay(Integer targetPaymentID, Integer serialNumber);

    void publishPayment(String content, Date limitDate, Integer fee);

    Page<Student> studentsNotPayed(Integer paymentID);

    void deletePayment(Integer id);
}
