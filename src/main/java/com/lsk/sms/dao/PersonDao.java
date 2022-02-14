package com.lsk.sms.dao;

import com.lsk.sms.model.Person;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface PersonDao {
    @Select("select * from persons")
    public List<Person> queryAllPerson();

    @Select("select * from persons where name=#{name}")
    public Person queryPersonByName(@Param("name") String name);

    @Select("select * from persons where id=#{id}")
    public Person queryPersonById(@Param("id") Integer id);

    @Insert("insert into persons(name,password,role,uuid) values(#{name},#{password},#{role},#{uuid})")
    void addPerson(Person person);

    @Update("update persons set password=#{password} where id=#{id}")
    public void updatePassword(@Param("password") String newPassword, @Param("id") Integer id);

    @Delete("delete from persons where id=#{id}")
    public void deletePersonById(@Param("id") Integer id);

    @Update("update persons set name=#{name} where id=#{id}")
    public void updateUsernameById(@Param("name") String name, @Param("id") Integer id);

    @Update("update persons set role=#{role} where id=#{id}")
    public void updateRoleById(@Param("role") String role, @Param("id") Integer id);

    @Delete("delete from persons where id=#{id}")
    void deletePerson(@Param("id") Integer id);

    @Select("select id from persons where uuid=#{uuid}")
    Integer getPersonIDByUUID(@Param("uuid") String uuid);
}
