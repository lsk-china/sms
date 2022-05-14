package com.lsk.smsbackend2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.Notice;
import com.lsk.smsbackend2.response.Bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository()
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("select id, title, content, publishDate, (select name from persons where id=publisher) as publisher, type from notices order by publishDate desc")
    Page<MixedNotice> queryAllNotices(Page<MixedNotice> page);

    @Select("select count(id) as hasReceived from receive_log where studentId=#{studentId} and noticeId=#{noticeId}")
    Boolean hasReceived(@Param("studentId") Integer studentId, @Param("noticeId") Integer noticeId);

    @Bean
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class MixedNotice {
        private Integer id;
        private String title;
        private String content;
        private Date publishDate;
        private String publisher;
        private String type;
    }
}
