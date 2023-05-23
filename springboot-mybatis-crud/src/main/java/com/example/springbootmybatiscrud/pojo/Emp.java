package com.example.springbootmybatiscrud.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Emp{
    private Integer id;//ID
    private String username;//用户名
    private String password;//
    private String name;//继名
    private Short gender;//性别，1男，2女
    private String image;//图像url
    private Short job;//职位，说明:1 班主任,2 讲师，3学工主管，4教研主管，5咨询师
    private LocalDate entrydate;//入职日期
    private Integer deptId;//部f门ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//修改时间
}
