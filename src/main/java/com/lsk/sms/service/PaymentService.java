package com.lsk.sms.service;

import com.lsk.sms.model.PayRecord;
import com.lsk.sms.model.Payment;
import com.lsk.sms.model.Student;

import java.util.Date;
import java.util.List;

public interface PaymentService {
    public List<Payment> allPayments();
    public List<PayRecord> myPayRecords();
    void pay(Integer targetPaymentID, Integer serialNumber);

    void publishPayment(String content, Date limitDate, Integer fee);

    List<Student> studentsNotPayed(Integer paymentID);
}
