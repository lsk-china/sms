package com.lsk.smsbackend2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsk.smsbackend2.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    @Select("select name from students where personID=#{personID}")
    String studentNameOfPersonId(@Param("personID") Integer personID);
}
