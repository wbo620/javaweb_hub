package com.example.springbootmybatis;

import com.example.springbootmybatis.mapper.UserMapper;
import com.example.springbootmybatis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisApplicationTests {
    @Autowired
private UserMapper userMapper;
    @Test
    public void testListUser(){
        List<User> users = userMapper.list();
        users.stream().forEach(user ->{
            System.out.println(user);
        } );
    }

}
