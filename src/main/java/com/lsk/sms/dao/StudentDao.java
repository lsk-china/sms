package com.lsk.sms.dao;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lsk.sms.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentDao {
    @Select("select * from students where name=#{name}")
    public Student queryStudentByName(@Param("name") String name);

    @Select("select * from students where id=#{id}")
    public Student queryStudentById(@Param("id") Integer id);

    @Delete("delete from students where id=#{id}")
    public void deleteStudentById(@Param("id") Integer id);

    @Select("select * from students where personID<>-1")
    public List<Student> queryAllStudents();

    @Update("update students set image=#{image} where id=#{id}")
    public void updateImageById(@Param("image") String image,@Param("id") Integer id);

    @Select("select * from students where matriculateNum=#{matriculateNum}")
    public Student queryStudentByMatriculateNum(@Param("matriculateNum") Integer matriculateNum);

    @Update("update students set name=#{name},address=#{address},telephone=#{telephone} where personID=#{personID}")
    public void updateStudent(@Param("name") String name, @Param("address") String address, @Param("telephone") String telephone, @Param("personID") Integer personID);

    @Select("select * from students where personID=#{personID}")
    public Student queryStudentByPersonID(@Param("personID") Integer personID);

    @Options(useGeneratedKeys = true)
    @Insert("insert into students(name, clazz, age, sex, dormitoryID, matriculateNum, address, telephone, personID) values(#{name}, #{clazz}, #{age}, #{sex}, #{dormitoryID}, #{matriculateNum}, #{address}, #{telephone}, #{personID})")
    void createStudent(Student student);

    @Update("update students set clazz=#{clazz},dormitoryID=#{dormitoryID},personID=#{personID} where id=#{id}")
    void completeStudent(@Param("clazz") String clazz, @Param("dormitoryID") Integer dormitoryID, @Param("personID") Integer personID, @Param("id") Integer id);

    @Select("select * from students where clazz=\"unset\"")
    List<Student> notReportedStudents();

    @Delete("delete from students where id=#{id}")
    void deleteStudent(@Param("id") Integer id);
}

