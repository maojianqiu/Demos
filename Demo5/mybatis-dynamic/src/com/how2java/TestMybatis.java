package com.how2java;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.how2java.pojo.Product;
 
public class TestMybatis {
 
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

//        System.out.println("----------------------------------------------");
//        System.out.println("��ѯ���е�");
//        List<Product> ps = session.selectList("listProduct");
//        for (Product p : ps) {
//			System.out.println(p);
//		}
//
//        System.out.println("----------------------------------------------");
//        System.out.println("ģ����ѯ");
//        Map<String,Object> params = new HashMap<>();
//        params.put("name","a");
//        List<Product> ps2 = session.selectList("listProduct",params);
//        for (Product p : ps2) {
//			System.out.println(p);
//		}

        System.out.println("----------------------------------------------");
        System.out.println("��������ѯ");
        Map<String,Object> params3 = new HashMap<>();
//        params3.put("name","a");
        params3.put("price","10");
        List<Product> ps3 = session.selectList("listProduct3",params3);
        for (Product p : ps3) {
            System.out.println(p);
        }

        System.out.println("----------------------------------------------");
        Product p = new Product();
        p.setId(6);
        p.setName("product zz");
        p.setPrice(99.99f);
        session.update("updateProduct",p);

        listAll(session);


        session.commit();
        session.close();
 
    }

    private static void listAll(SqlSession session) {
        Map<String,Object> params = new HashMap<>();
//        params.put("name","a");
//        params.put("price","10");
        List<Product> ps2 = session.selectList("listProduct",params);
        for (Product p : ps2) {
            System.out.println(p);
        }
    }
}