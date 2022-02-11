package com.lsk.sms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRecord {
    private Integer id;
    private Integer studentID;
    private Integer serialNumber;
    private Date operateDate;
    private Integer targetPaymentID;
}
