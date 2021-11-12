package com.vea.springbootnew;

import com.vea.springbootnew.mapper.CategoryMapper;
import com.vea.springbootnew.pojo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootNewApplicationTests {

    @Autowired
    CategoryMapper dao;

    @Test
    public void test() {
        List<Category> cs=  dao.findAll();
        for (Category c : cs) {
            System.out.println("c.getName():"+ c.getName());
        }

    }

}
