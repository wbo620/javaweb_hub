package com.example.javawebspringquickstart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.NestingKind;

@RestController
public class HelloContorlle {
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello world!");
        return "Hello";
    }
}
