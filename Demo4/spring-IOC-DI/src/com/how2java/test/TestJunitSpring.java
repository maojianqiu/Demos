package com.how2java.test;

import com.how2java.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * description: TestJunitSpring <br>
 * date: 2021/11/4 20:57 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestJunitSpring {
    @Autowired
    Category c;

    @Test
    public void test(){
        System.out.println(c.getName());
    }
}
