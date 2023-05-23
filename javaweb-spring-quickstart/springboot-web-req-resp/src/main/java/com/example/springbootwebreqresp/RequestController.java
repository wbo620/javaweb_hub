package com.example.springbootwebreqresp;

import com.example.springbootwebreqresp.pojo.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class RequestController {
    //1.简单参数
   /* @RequestMapping("/SimpleParam")
    public String SimpleParam(String name, Integer age) {

        System.out.println(name + ": " + age);
        return "ok";
    }*/
    //简单参数,用ReqParam完成映射
    @RequestMapping("/SimpleParam")
    public String SimpleParam(@RequestParam(name = "name", required = false) String name, Integer age) {

        System.out.println(name + ": " + age);
        return "ok";
    }

    //2.实体参数
    @RequestMapping("/SimplePojo")
    public String SimplePojo(User user) {
        System.out.println(user);
        return "ok";
    }

    //复杂参数
    @RequestMapping("/ComplexPojo")
    public String ComplexPojo(User user) {
        System.out.println(user);
        return "ok";
    }

    //数组参数
    @RequestMapping("/ArrayPojo")
    public String ArrayPojo(String[] hobby) {
        System.out.println(Arrays.toString(hobby));
        return "ok";
    }

    //集合参数
    @RequestMapping("/ListPojo")
    public String ListPojo(@RequestParam List<String> list) {
        System.out.println(list);
        return "ok";
    }

    //日期时间参数
    @RequestMapping("/dateParam")
    public String dateParam(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updateTime) {
        System.out.println(updateTime);
        return "OK";
    }


    //json参数
    @RequestMapping("/jsonParam")
    public String jsonParam(@RequestBody User user) {
        System.out.println(user);
        return "OK";
    }

    //单一路径参数
    @RequestMapping("/path/{id}")
    public String PathParam(@PathVariable Integer id) {
        System.out.println(id);
        return "OK";
    }

    //多路径参数
    @RequestMapping("/path/{id}/{name}")
    public String PathParam2(@PathVariable Integer id, @PathVariable String name) {
        System.out.println(id);
        System.out.println(name);
        return "OK";
    }
}