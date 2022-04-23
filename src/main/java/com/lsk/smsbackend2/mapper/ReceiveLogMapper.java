package com.lsk.smsbackend2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.ReceiveLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReceiveLogMapper extends BaseMapper<ReceiveLog> {
    @Select("select name from students where id not in (select studentId from receive_log where noticeId=#{noticeId})")
    Page<String> notReceivedStudents(Page page ,@Param("noticeId") Integer noticeId);
}
