package com.lsk.sms.service.impl;

import com.lsk.sms.dao.PaymentDao;
import com.lsk.sms.dao.StudentDao;
import com.lsk.sms.model.PayRecord;
import com.lsk.sms.model.Payment;
import com.lsk.sms.model.Student;
import com.lsk.sms.redis.RedisDao;
import com.lsk.sms.redis.StudentCache;
import com.lsk.sms.service.PaymentService;
import com.lsk.sms.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Payment> allPayments() {
        return paymentDao.queryAllPayments();
    }

    @Override
    public List<PayRecord> myPayRecords() {
        String name = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Integer studentID = StudentCache.studentID(name);
        return paymentDao.queryPayRecord(studentID);
    }

    @Override
    public void pay(Integer targetPaymentID, Integer serialNumber) {
        String name = SecurityUtil.currentUsername();
        Integer studentID = StudentCache.studentID(name);
        PayRecord payRecord = new PayRecord();
        payRecord.setOperateDate(new Date());
        payRecord.setStudentID(studentID);
        payRecord.setTargetPaymentID(targetPaymentID);
        payRecord.setSerialNumber(serialNumber);
        paymentDao.createPayRecord(payRecord);
        paymentDao.increasePayedCount(targetPaymentID);
    }

    @Override
    public void publishPayment(String content, Date limitDate, Integer fee) {
        Payment payment = new Payment();
        payment.setPublishDate(new Date());
        payment.setContent(content);
        payment.setFee(fee);
        payment.setLimitDate(limitDate);
        paymentDao.createPayment(payment);
    }

    @Override
    public List<Student> studentsNotPayed(Integer paymentID) {
        List<Student> result = new ArrayList<>();
        List<Student> allStudents = studentDao.queryAllStudents();
        List<Integer> payedStudentIDs = paymentDao.queryPayRecordIDsByTargetID(paymentID);
        for (Student student : allStudents) {
            if (! payedStudentIDs.contains(student.getId())) {
                result.add(student);
            }
        }
        return result;
    }

    @Override
    public void deletePayment(Integer id) {
        paymentDao.deletePayment(id);
    }
}
