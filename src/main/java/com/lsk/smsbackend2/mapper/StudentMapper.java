package com.lsk.smsbackend2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    @Select("select * from students where id not in (select studentID from pay_record where targetPaymentID=#{targetPaymentId})")
    Page<Student> queryNotPayedStudents (Page<Student> page, @Param("targetPaymentId") Integer targetPaymentId);

    Integer admitStudentBatch(Collection<Student> students);
}
