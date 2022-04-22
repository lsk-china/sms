package com.lsk.smsbackend2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.helper.StudentHelper;
import com.lsk.smsbackend2.mapper.PayRecordMapper;
import com.lsk.smsbackend2.mapper.PaymentMapper;
import com.lsk.smsbackend2.model.PayRecord;
import com.lsk.smsbackend2.model.Payment;
import com.lsk.smsbackend2.model.Student;
import com.lsk.smsbackend2.redis.RedisDao;
import com.lsk.smsbackend2.service.PaymentService;
import com.lsk.smsbackend2.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
    @Value("${paging.items-per-page}")
    private Integer itemsPerPage;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PayRecordMapper payRecordMapper;

    @Autowired
    private StudentHelper studentHelper;

    @Autowired
    private RedisDao redisDao;

    @Override
    public Page<Payment> allPayments(Integer page) {
        return paymentMapper.selectPage(
                new Page<>(page, itemsPerPage),
                null
        );
    }

    @Override
    public Page<PayRecord> myPayRecords(Integer page) {
        Integer currentStudentId = studentHelper.currentStudentId();
        return payRecordMapper.selectPage(
                new Page<PayRecord>(page, itemsPerPage),
                new QueryWrapper<PayRecord>().eq("studentID", currentStudentId)
        );
    }

    @Override
    public void pay(Integer targetPaymentID, Integer serialNumber) {
        Integer studentID = studentHelper.currentStudentId();
        PayRecord payRecord = new PayRecord();
        payRecord.setOperateDate(new Date());
        payRecord.setStudentID(studentID);
        payRecord.setStudentID(serialNumber);
        payRecord.setTargetPaymentID(targetPaymentID);
        payRecordMapper.insert(payRecord);
        paymentMapper.increasePayedCount(targetPaymentID);
    }

    @Override
    public void publishPayment(String content, Date limitDate, Integer fee) {
        Payment payment = new Payment();
        payment.setPublishDate(new Date());
        payment.setContent(content);
        payment.setFee(fee);
        payment.setLimitDate(limitDate);
        paymentMapper.insert(payment);
    }

    @Override
    public void deletePayment(Integer id) {
        paymentMapper.deleteById(id);
    }
}
