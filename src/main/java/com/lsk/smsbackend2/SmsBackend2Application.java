package com.lsk.smsbackend2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class SmsBackend2Application {

    public static void main(String[] args) {
        SpringApplication.run(SmsBackend2Application.class, args);
    }

}
