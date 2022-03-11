package com.lsk.sms.dao;

import com.lsk.sms.model.ReceiveLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReceiveLogDao {
    @Select("select studentId from receive_log where noticeId=#{noticeID}")
    List<Integer> queryStudentIDs(@Param("noticeID") Integer noticeID);
    @Insert("insert into receive_log(studentId, receiveDate, noticeId) values(#{studentId}, #{receiveDate}, #{noticeId})")
    void addReceiveLog(ReceiveLog receiveLog);
    @Select("select * from receive_log where noticeId=#{noticeID} and studentId=#{studentID}")
    ReceiveLog queryReceiveLog(@Param("noticeID") Integer noticeID, @Param("studentID") Integer studentID);
}
