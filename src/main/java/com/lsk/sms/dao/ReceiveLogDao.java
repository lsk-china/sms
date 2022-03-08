package com.lsk.sms.dao;

import com.lsk.sms.model.ReceiveLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReceiveLogDao {
    @Select("select student_id from receive_log where notice_id=#{noticeID}")
    List<Integer> queryStudentIDs(@Param("noticeID") Integer noticeID);
    @Insert("insert into receive_log(student_id, receive_date, notice_id) values(#{student_id}, #{receive_date}, #{notice_id})")
    void addReceiveLog(ReceiveLog receiveLog);
}
