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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("receive_log")
public class ReceiveLog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("studentId")
    private Integer studentId;
    @TableField("receiveDate")
    private Date receiveDate;
    @TableField("noticeId")
    private Integer noticeId;
}
