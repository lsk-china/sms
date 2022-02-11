package com.lsk.sms.controller;

import com.lsk.sms.model.Payment;
import com.lsk.sms.model.Student;
import com.lsk.sms.response.JsonReturn;
import com.lsk.sms.service.PaymentService;
import com.lsk.sms.service.PersonService;
import com.lsk.sms.service.StudentService;
import com.lsk.sms.util.HashUtil;
import com.lsk.sms.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class Controller {

    @Autowired
    private PersonService personService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PaymentService paymentService;

//    @GetMapping("/")
//    public void index(HttpServletResponse resp) throws IOException {
//        if(RoleUtils.getRole(SecurityContextHolder.getContext().getAuthentication().getAuthorities()) == Roles.ADMIN){
//            resp.sendRedirect("/admin");
//        }else{
//            resp.sendRedirect("/student");
//        }
//    }
//
//    @GetMapping("admin")
//    @Admin
//    public ModelAndView admin(HttpServletResponse resp,ModelAndView mav){
//        List<com.lsk.sms.model.Student> students = studentDao.queryAllStudents();
//        mav.addObject("students",students);
//        mav.setViewName("admin.html");
//        return mav;
//    }
//    @GetMapping("student")
//    @Student
//    public ModelAndView student(HttpServletResponse resp,ModelAndView mav){
//        com.lsk.sms.model.Student student = studentDao.queryStudentByName(SecurityUtils.getName());
//        mav.addObject("student",student);
//        mav.addObject("image","http://localhost:10001/sms/img/"+student.getImage());
//        mav.setViewName("student.html");
//        return mav;
//    }
//    @GetMapping("addstudent")
//    @Admin
//    public void addStudent(HttpServletResponse resp, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("age") Integer age, @RequestParam("sex") String sex, @RequestParam("grade") String grade, @RequestParam("clazz") String clazz) throws IOException {
//        Person person = new Person();
//        person.setName(name);
//        person.setPassword(password);
//        person.setRole(Roles.Student.name());
//        com.lsk.sms.model.Student student = new com.lsk.sms.model.Student();
//        student.setAge(age);
//        student.setClazz(clazz);
//        student.setGrade(grade);
//        student.setSex(sex);
//        student.setName(name);
//        personDao.addPerson(person);
//        studentDao.addStudent(student);
//        resp.sendRedirect("/admin");
//    }
//    @GetMapping("removestudent")
//    @Admin
//    public void removeStudent(HttpServletResponse resp,@RequestParam("name") String name) throws IOException {
//        com.lsk.sms.model.Student student = studentDao.queryStudentByName(name);
//        Integer studentId = student.getId();
//        Person person = personDao.queryPersonByName(name);
//        Integer personId = person.getId();
//        studentDao.deleteStudentById(studentId);
//        personDao.deletePersonById(personId);
//        resp.sendRedirect("/admin");
//    }
//    @GetMapping("updatestudent")
//    @Admin
//    public void updateStudent(HttpServletResponse resp,HttpServletRequest req,@RequestParam("name") String name,@RequestParam("password") String password,@RequestParam("age") Integer age,@RequestParam("grade") String grade,@RequestParam("clazz") String clazz) throws IOException {
//        Person person = personDao.queryPersonByName(name);
//        com.lsk.sms.model.Student student = studentDao.queryStudentByName(name);
//        String destPassword = password == null || password.equals("") ? person.getPassword() : password;
//        String destGrade = grade == null || grade.equals("") ? student.getGrade() : grade;
//        Integer destAge = age == null ? student.getAge() : age;
//        String destClazz = clazz == null || clazz.equals("") ? student.getClazz() : clazz;
//        Person destPerson = new Person(person.getId(),name,destPassword,person.getRole());
//        com.lsk.sms.model.Student destStudent = new com.lsk.sms.model.Student(student.getId(),name,destGrade,destClazz,destAge,student.getSex(),student.getImage());
//        personDao.updatePersonById(destPerson);
//        studentDao.updateStudentById(destStudent);
//        resp.sendRedirect("/admin");
//    }
//
//    @GetMapping("updatestudent_s")
//    @Student
//    public void updateStudentForStudent(HttpServletResponse resp,HttpServletRequest req,@RequestParam("password") String password) throws IOException {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        Person person = personDao.queryPersonByName(name);
//        Person destPerson = new Person(person.getId(),name,password,person.getRole());
//        personDao.updatePersonById(destPerson);
//        resp.sendRedirect("/student");
//    }
////    @GetMapping("updateimage")
////    @Student
////    public void updateImage(@RequestParam("image") String image){
////        String name = SecurityUtils.getName();
////        Integer id = studentDao.queryStudentByName(name).getId();
////        studentDao.updateImageById(image,id);
////    }
//    @PostMapping("updateimage")
//
//    public void updateimage(HttpServletResponse resp,@RequestParam("file") MultipartFile file) throws IOException {
//        if(file == null){
//            Cookie cookie = new Cookie("reason","请选择一个文件。");
//            resp.addCookie(cookie);
//            resp.sendRedirect("/student");
//            return;
//        }
//        String fileName = file.getOriginalFilename();
//        String path = "D://java/server/sms/img/";
//        log.info(path+fileName);
//        File destFile = new File(path+fileName);
//        try{
//            file.transferTo(destFile);
//        }catch(Exception e){
//            Cookie cookie = new Cookie("reason","上传失败。");
//            resp.addCookie(cookie);
//            resp.sendRedirect("/student");
//            return;
//        }
//        String name = SecurityUtils.getName();
//        Integer id = studentDao.queryStudentByName(name).getId();
//        studentDao.updateImageById(fileName,id);
//        resp.sendRedirect("/student");
//    }
    @JsonReturn
    @GetMapping("/admin/personList")
    public Object personList() {
        return personService.queryAllPersons();
    }

    @JsonReturn
    @GetMapping("/alreadyLogin")
    public Object alreadyLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ! (authentication instanceof AnonymousAuthenticationToken);
    }

    @JsonReturn
    @GetMapping("/student/info")
    public Object studentInfo() {
        return studentService.info();
    }

    @JsonReturn
    @PostMapping("/student/updateInfo")
    public Object updateInfo(String name, String address, String telephone) {
        studentService.updateInfo(name, address, telephone);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/admin/queryStudentByMatriculate")
    public Object queryStudentByMatriculate(Integer matriculate) {
        return studentService.findStudentByMatriculateNum(matriculate);
    }
    
    @JsonReturn
    @PostMapping("/admin/admitStudent")
    public Object admitStudent(String name, Integer age, Integer sex, Integer matriculateNum, String address, Integer telephone) {
        studentService.admitStudent(name, age, sex, matriculateNum, address, telephone);
        return "Success";
    }

    @JsonReturn
    @PostMapping("/admin/studentReport")
    public Object studentReport(Integer matriculateNum, String clazz, Integer dormitoryID) {
        Map<String, Object> result = new HashMap<>();
        String temporaryUsername = StringUtil.randomString(10);
        result.put("temporaryUsername", temporaryUsername);
        String temporaryPassword = StringUtil.randomString(10);
        result.put("temporaryPassword", temporaryPassword);
        Integer personID = personService.createStudent(temporaryUsername, temporaryPassword);
        studentService.studentReport(clazz, dormitoryID, personID, matriculateNum);
        return "Success";
    }

    @JsonReturn
    @PostMapping("/student/pay")
    public Object studentPay(Integer targetPaymentID, Integer serialNumber) {
        paymentService.pay(targetPaymentID, serialNumber);
        return "Success";
    }

    @JsonReturn
    @PostMapping("/changePassword")
    public Object changePassword(String newPassword) {
        personService.updatePassword(newPassword);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/changeUsername")
    public Object changeUsername(String newUsername) {
        personService.updateUsername(newUsername);
        return "Success";
    }

    @JsonReturn
    @PostMapping("/finance/publishPayment")
    public Object publishPayment(String content, Integer fee, String limitDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date limitDateObject = dateFormat.parse(limitDate);
        paymentService.publishPayment(content, limitDateObject, fee);
        return "Success";
    }

    @JsonReturn
    @PostMapping("/admin/createPerson")
    public Object createPerson(String username, String password, String role) {
        Integer personID = personService.createPerson(username, password);
        personService.grantPerson(personID, role);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/admin/grant")
    public Object grant(Integer targetID, String role) {
        personService.grantPerson(targetID, role);
        return "Success";
    }

    @JsonReturn
    @GetMapping("/payments")
    public Object payments() {
        return paymentService.allPayments();
    }

    @JsonReturn
    @GetMapping("/student/myPayRecords")
    public Object myPayRecords() {
        return paymentService.myPayRecords();
    }
}
