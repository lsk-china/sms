package com.lsk.smsbackend2.model;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Integer id;
    private Integer fee;
    private String content;
    private Date publishDate;
    private Date limitDate;
    private Integer payedCount;
}
