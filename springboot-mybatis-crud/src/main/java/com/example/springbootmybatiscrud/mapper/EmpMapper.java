package com.example.springbootmybatiscrud.mapper;

import com.example.springbootmybatiscrud.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
    //删除操作
    @Delete("delete from emp where id=#{id}")
    public void delete(Integer id);

    //根据id批量删除
    public void deleteByids(List<Integer> ids);

    //插入操作
    @Options(keyProperty = "id", useGeneratedKeys = true)//获取返回的主键
    //新增员工
    @Insert("insert into emp(username,name,gender,image,job,entrydate,dept_id,create_time,update_time)" +
            " values (#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    public void insert(Emp emp);

    //更新操作
    //@Update("update emp set username=#{username},name=#{name},gender=#{gender},image=#{image},job=#{job}" +
    //        "entrydate=#{entrydate},dept_id=#{deptId},update_time=#{updateTime} where id=#{id}")
    public void update(Emp emp);

    //根据id 查询数据
    //小问题:有些字段没有封装成功,因为对象中的属性名与数据库中的字段名不一致,
    // 导致封装不成功
    //@Select("select * from emp where id=#{id}")
    //public Emp select(Integer id);

    //方案1:取别名:给不一致的字段取别名,让别名与字段名一致
    //@Select("select id, username, password, name, gender, image, job, entrydate, " +
    //        "dept_id deptId, create_time createTime, update_time updateTime from emp where id=#{id}")
   // public Emp select(Integer id);

    //方案二：手动结果映射:通过@Results,@Result注解手动映射封装
   /* @Results({
            @Result(column= "dept id",property= "deptId"),
            @Result(column= "create_time",property= "createTime"),
            @Result (column= "update time",property ="updateTime")
    })
    @Select ("select *from emp where id= #{id}")
    public Emp select(Integer id);*/

    //方案三:在配置文件中开启驼峰命名
    /*  @Select("select * from emp where id=#{id}")
    public Emp select(Integer id);*/

    //多条件查询数据
    //小问题:%${name}%,sql注入,不安全
    //@Select("select * from emp where name like '%${name}%' and gender=#{gender} and " +
    //        "entrydate between #{begin} and #{end} order by entrydate desc")

    //改进,利用concat函数进行字符串拼接,解决sql注入的问题
    //@Select("select * from emp where name like concat('%',#{name},'%') and gender=#{gender} and " +
    //        "entrydate between #{begin} and #{end} order by entrydate desc")

    //还可以写进一个xml文件中--->src/main/resources/com/example/springbootmybatiscrud/mapper/EmpMapper.xml
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);


}


