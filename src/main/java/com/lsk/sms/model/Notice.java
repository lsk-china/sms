package com.lsk.sms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private Date publishDate;
    private Integer publisher;
    private String type;
}
