package com.example.springbootmybatis.mapper;

import com.example.springbootmybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from mybatis.user")
    public List<User> list();
}
