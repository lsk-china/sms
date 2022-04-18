package com.lsk.smsbackend2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveLog {
    private Integer id;
    private Integer studentId;
    private Date receiveDate;
    private Integer noticeId;
}
