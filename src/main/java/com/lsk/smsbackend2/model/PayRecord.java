package com.lsk.smsbackend2.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lsk.smsbackend2.response.Bean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Bean
@TableName("pay_record")
public class PayRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("studentID")
    private Integer studentID;
    @TableField("serialNumber")
    private Integer serialNumber;
    @TableField("operateDate")
    private Date operateDate;
    @TableField("targetPaymentID")
    private Integer targetPaymentID;

    public PayRecord() {
    }

    public PayRecord(Integer id, Integer studentID, Integer serialNumber, Date operateDate, Integer targetPaymentID) {
        this.id = id;
        this.studentID = studentID;
        this.serialNumber = serialNumber;
        this.operateDate = operateDate;
        this.targetPaymentID = targetPaymentID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Integer getTargetPaymentID() {
        return targetPaymentID;
    }

    public void setTargetPaymentID(Integer targetPaymentID) {
        this.targetPaymentID = targetPaymentID;
    }
}
