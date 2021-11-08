package com.how2java;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.how2java.pojo.Order;
import com.how2java.pojo.OrderItem;
import com.how2java.pojo.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
 
import com.how2java.pojo.Category;
 
public class TestMybatis {
    /**
     * 1. 应用程序找Mybatis要数据
     * 2. mybatis从数据库中找来数据
     * 2.1 通过mybatis-config.xml 定位哪个数据库
     * 2.2 通过Category.xml执行对应的select语句
     * 2.3 基于Category.xml把返回的数据库记录封装在Category对象中
     * 2.4 把多个Category对象装在一个Category集合中
     * 3. 返回一个Category集合
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

//        Category c = new Category();
//        c.setName("新增加的Category");
//        session.insert("addCategory",c);
//
//        listAll(session);
//
//        session.commit();
//
//        System.out.println("---------------------------------------------");
//        Category cdelete = new Category();
//        cdelete.setId(4);
//        session.delete("deleteCategory",cdelete);
//
//        listAll(session);
//
//        session.commit();
//
//        System.out.println("---------------------------------------------");
//        Category cgetone= session.selectOne("getCategory",3);
//
//        System.out.println(cgetone.getName());
//
//        System.out.println("---------------------------------------------");
//        Category cupdate= session.selectOne("getCategory",3);
//        cupdate.setName("修改了的Category名稱");
//        session.update("updateCategory",cupdate);
//
//        listAll(session);

//        System.out.println("---------------------------------------------");
//        List<Category> cs = session.selectList("listCategoryAndProduct");
//        for (Category c : cs) {
//            System.out.println(c);
//            List<Product> ps = c.getProducts();
//            for (Product p : ps) {
//                System.out.println("\t"+p);
//            }
//        }
//
//        System.out.println("---------------------------------------------");
//        List<Product> ps = session.selectList("listProductAndCategory");
//        for (Product p : ps) {
//            System.out.println(p+" 对应的分类是 \t "+ p.getCategory().getName());
//        }

        System.out.println("---------------------------------------------");
        addOrderItem(session);
        listOrder(session);



        session.close();

    }


    private static void deleteOrderItem(SqlSession session) {
        Order o1 = session.selectOne("getOrder",1);
        Product p6 = session.selectOne("getProduct",6);
        OrderItem oi = new OrderItem();
        oi.setProduct(p6);
        oi.setOrder(o1);
        session.delete("deleteOrderItem", oi);
    }

    private static void addOrderItem(SqlSession session) {
        Order o1 = session.selectOne("getOrder", 1);
        Product p6 = session.selectOne("getProduct", 6);
        OrderItem oi = new OrderItem();
        oi.setProduct(p6);
        oi.setOrder(o1);
        oi.setNumber(200);

        session.insert("addOrderItem", oi);
    }

    private static void listOrder(SqlSession session) {
        List<Order> os = session.selectList("listOrder");
        for (Order o : os) {
            System.out.println(o.getCode());
            List<OrderItem> ois = o.getOrderItems();
            for (OrderItem oi : ois) {
                System.out.format("\t%s\t%f\t%d%n", oi.getProduct().getName(), oi.getProduct().getPrice(),
                        oi.getNumber());
            }
        }
    }

    private static void listAll(SqlSession session) {
        List<Category> cs = session.selectList("listCategory");
        for (Category c : cs) {
            System.out.println(c.getName());
        }
    }
}