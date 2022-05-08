package com.lsk.smsbackend2.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lsk.smsbackend2.response.Bean;
import lombok.*;

import java.util.Date;

@Bean
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("payments")
public class Payment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer fee;
    private String content;
    @TableField("publishDate")
    private Date publishDate;
    @TableField("limitDate")
    private Date limitDate;
    @TableField("payedCount")
    private Integer payedCount;
}
