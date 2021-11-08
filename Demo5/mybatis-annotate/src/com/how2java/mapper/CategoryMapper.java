package com.how2java.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.*;

import com.how2java.pojo.Category;
 /*
 *
 * */
public interface CategoryMapper {
    /*
    * @Select注解获取Category类本身
    * @Results 通过@Result和@Many中调用ProductMapper.listByCategory()方法相结合，来获取一对多关系
    * column为数据库字段名，porperty为实体类属性名，jdbcType为数据库字段数据类型，id为是否为主键。
    * */
     @Select(" select * from category_ ")
     @Results({
             @Result(property = "id", column = "id"),
             @Result(property = "products", javaType = List.class, column = "id", many = @Many(select = "com.how2java.mapper.ProductMapper.listByCategory") )
     })
     public List<Category> list();

 
    @Insert(" insert into category_ ( name ) values (#{name}) ")  
    public int add(Category category);  
       
    @Delete(" delete from category_ where id= #{id} ")  
    public void delete(int id);  
       
    @Select("select * from category_ where id= #{id} ")  
    public Category get(int id);  
     
    @Update("update category_ set name=#{name} where id=#{id} ")  
    public int update(Category category);   
       
//    @Select(" select * from category_ ")
//    public List<Category> list();

     @Select(" select * from category_ limit #{start},#{count}")
     public List<Category> listByPage(@Param("start") int start, @Param("count")int count);
}