package com.lsk.sms.dao;

import com.lsk.sms.model.PayRecord;
import com.lsk.sms.model.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentDao {
    @Select("select * from payments")
    List<Payment> queryAllPayments();
    @Insert("insert into payments(content, publishDate, limitDate, fee) values(#{content}, #{publishDate}, #{limitDate}, #{fee})")
    void createPayment(Payment payment);
    @Update("update payments set payedCount=payedCount+1 where id=#{id}")
    void increasePayedCount(@Param("id") Integer id);

    @Select("select * from pay_record")
    List<PayRecord> queryAllPayRecord();
    @Select("select * from pay_record where studentID=#{studentID}")
    List<PayRecord> queryPayRecord(@Param("studentID") Integer studentID);
    @Select("select * from pay_record where targetPaymentID=#{targetPaymentID}")
    List<PayRecord> queryPayRecordByTargetID(@Param("targetPaymentID") Integer paymentID);
    @Insert("insert into pay_record(studentID, opreateDate, targetPaymentID, serialNumber) values(#{studentID}, #{opreateDate}, #{targetPaymentID}, #{serialNumber})")
    void createPayRecord(PayRecord payRecord);
    @Select("select id from pay_record where targetPaymentID=#{targetPaymentID}")
    List<Integer> queryPayRecordIDsByTargetID(@Param("targetPaymentID") Integer targetPaymentID);

}
