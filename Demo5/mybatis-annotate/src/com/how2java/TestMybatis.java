package com.how2java;
  
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.mapper.OrderMapper;
import com.how2java.mapper.ProductMapper;
import com.how2java.pojo.Order;
import com.how2java.pojo.OrderItem;
import com.how2java.pojo.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.how2java.mapper.CategoryMapper;
import com.how2java.pojo.Category;
  
public class TestMybatis {
  
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        CategoryMapper mapper = session.getMapper(CategoryMapper.class);

        PageHelper.offsetPage(0, 5);
        List<Category> cs = mapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
        }
        PageInfo pageInfo = new PageInfo<>(cs);
        System.out.println("总数："+pageInfo.getTotal());
        System.out.println(pageInfo);
//        add(mapper);
//        delete(mapper);
//        get(mapper);
//        update(mapper);
//        listCPAll(mapper);

//        ProductMapper mapper = session.getMapper(ProductMapper.class);
////
////        List<Product> ps= mapper.list();
////        for (Product p : ps) {
////            System.out.println(p + "\t对应的分类是:\t" + p.getCategory().getName());
////        }

//        listOrder(session);
             
        session.commit();
        session.close();
  
    }
 
    private static void update(CategoryMapper mapper) {
        Category c= mapper.get(8);
        c.setName("修改了的Category名稱");
        mapper.update(c);
        listAll(mapper);
    }
 
    private static void get(CategoryMapper mapper) {
        Category c= mapper.get(8);
        System.out.println(c.getName());
    }
 
    private static void delete(CategoryMapper mapper) {
        mapper.delete(2);
        listAll(mapper);
    }
 
    private static void add(CategoryMapper mapper) {
        Category c = new Category();
        c.setName("新增加的Category");
        mapper.add(c);
        listAll(mapper);
    }
  
    private static void listAll(CategoryMapper mapper) {
        List<Category> cs = mapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
        }
    }

    private static void listCPAll(CategoryMapper mapper) {
        List<Category> cs = mapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
            List<Product> ps = c.getProducts();
            for (Product p : ps) {
                System.out.println("\t"+p.getName());
            }
        }
    }

    private static void listOrder(SqlSession session) {
        OrderMapper mapper =session.getMapper(OrderMapper.class);
        List<Order> os = mapper.list();
        for (Order o : os) {
            System.out.println(o.getCode());
            List<OrderItem> ois= o.getOrderItems();
            if(null!=ois){
                for (OrderItem oi : ois) {
                    System.out.format("\t%s\t%f\t%d%n", oi.getProduct().getName(),oi.getProduct().getPrice(),oi.getNumber());
                }
            }

        }
    }
}