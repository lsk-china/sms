package com.lsk.sms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class PayRecord {
    private Integer id;
    private Integer studentID;
    private Integer serialNumber;
    private Date operateDate;
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
