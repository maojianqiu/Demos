1.新增加接口CategoryMapper ，并在接口中声明的方法上，加上注解
 CategoryMapper mapper = session.getMapper(CategoryMapper.class);
 

2.一对多
    /*
    * @Select注解获取Category类本身
    * @Results 通过@Result和@Many中调用ProductMapper.listByCategory()方法相结合，来获取一对多关系
    * */

3.多对一

4.多对多

5.动态sql

6.