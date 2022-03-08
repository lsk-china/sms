package com.lsk.sms.dao;

import com.lsk.sms.model.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeDao {
    @Select("select * from notices")
    List<Notice> queryAllNotices();
    @Insert("insert into notices(title, content, publishDate, publisher, type) values(#{title}, #{content}, #{publishDate}, #{publisher}, #{type})")
    void addNotice(Notice notice);
    @Delete("delete from notices where id=#{id}")
    void deleteNotice(@Param("id") Integer id);
}
