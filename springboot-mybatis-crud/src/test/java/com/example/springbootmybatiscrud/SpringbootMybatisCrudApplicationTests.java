package com.example.springbootmybatiscrud;

import com.example.springbootmybatiscrud.mapper.EmpMapper;
import com.example.springbootmybatiscrud.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.invoke.CallSite;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringbootMybatisCrudApplicationTests {
    @Autowired
    private EmpMapper empMapper;
     @Test
     public void testDelete() {
         //根据id删除
         //empMapper.delete(2);
         //根据id批量删除
         List<Integer> ids= Arrays.asList(17,22,24);
         empMapper.deleteByids(ids);
     }

    @Test
    public void testInsert() {
        Emp emp =new Emp ();
        emp.setUsername("Tom6");
        emp.setName("汤姆6");
        emp.setImage ("7.jpg");
        emp.setGender((short)1);
        emp.setJob ((short)1);
        emp.setEntrydate (LocalDate.of(2000,1,1));
        emp.setCreateTime (LocalDateTime.now());
        emp.setUpdateTime (LocalDateTime.now());
        emp.setDeptId(1);

       // 插入操作
        empMapper.insert(emp);
        System.out.println(emp.getId());

    }

    @Test
    public void testUpdate() {
         /* 更新操作
        empMapper.update(emp);*/
        //通过id查询
        //Emp emp1 = empMapper.select(1);
        //System.out.println(emp1);
        Emp emp =new Emp ();
        emp.setId(1);
        emp.setUsername("Tom888");
        emp.setName("汤姆688");
        emp.setImage ("7.jpg");
        emp.setGender((short)2);
        emp.setUpdateTime (LocalDateTime.now());

         empMapper.update(emp);


    }
    @Test
    public void testList(){
      //多条件查询
            List<Emp> empList = empMapper.list(null, (short)1, LocalDate.of(2010, 1, 1), LocalDate.of(2020, 1, 1));
            System.out.println(empList);
    }

}
