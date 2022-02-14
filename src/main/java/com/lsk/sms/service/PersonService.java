package com.lsk.sms.service;

import com.lsk.sms.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> queryAllPersons();
    void updatePassword(String newPassword);
    Integer createPerson(String username, String password);
    void updateUsername(String newUsername);

    void grantPerson(Integer targetID, String role);

    Integer createStudent(String username, String password);

    void deletePerson(Integer id);
}
