package com.lsk.sms.service.impl;

import com.lsk.sms.dao.PersonDao;
import com.lsk.sms.model.Person;
import com.lsk.sms.redis.RedisDao;
import com.lsk.sms.response.StatusCode;
import com.lsk.sms.service.PersonService;
import com.lsk.sms.util.HashUtil;
import com.lsk.sms.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service("personService")
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Autowired
    private RedisDao redisDao;

    @Override
    public List<Person> queryAllPersons() {
        return personDao.queryAllPerson();
    }

    @Override
    public void updatePassword(String newPassword) {
        String newPasswordHash = HashUtil.sha256String(newPassword);
        String username = SecurityUtil.currentUsername();
        Integer id = Integer.parseInt(redisDao.get(username + "-ID"));
        personDao.updatePassword(newPasswordHash, id);
    }
    @Override
    public void updateUsername(String newUsername) {
        Person person = personDao.queryPersonByName(newUsername);
        if (person != null) {
            throw new StatusCode(500, "Person exists.");
        }
        String username = SecurityUtil.currentUsername();
        Integer id = Integer.parseInt(redisDao.get(username + "-ID"));
        personDao.updateUsernameById(newUsername, id);
    }
    @Override
    public Integer createPerson(String username, String password){
        Person person = new Person();
        person.setName(username);
        person.setPassword(HashUtil.sha256String(password));
        person.setRole("USER");
        person.setUuid(UUID.randomUUID().toString());
        personDao.addPerson(person);
        Integer id = personDao.getPersonIDByUUID(person.getUuid());
        return id;
    }
    @Override
    public void grantPerson(Integer targetID, String role) {
        log.debug(targetID + " -> " + role);
        personDao.updateRoleById(role, targetID);
    }

    @Override
    public Integer createStudent(String username, String password) {
        Person person = new Person();
        person.setName(username);
        person.setPassword(HashUtil.sha256String(password));
        person.setRole("STUDENT");
        person.setUuid(UUID.randomUUID().toString());
        personDao.addPerson(person);
        return personDao.getPersonIDByUUID(person.getUuid());
    }

    @Override
    public void deletePerson(Integer id) {
        personDao.deletePerson(id);
    }

    @Override
    public String name(Integer id) {
        Person person = personDao.queryPersonById(id);
        return person.getName();
    }
}
