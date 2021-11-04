package com.how2java.test;

import com.how2java.pojo.Product;
import com.how2java.service.ProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.how2java.pojo.Category;

public class TestSpring {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext04.xml" });

		Category c = (Category) context.getBean("c");

		System.out.println(c.getName());

		System.out.println("---------------------------------------------------------------------");

		Product p = (Product) context.getBean("p");

		System.out.println(p.getName());
		System.out.println(p.getCategory().getName());


		System.out.println("---------------------------------------------------------------------");

		ProductService s = (ProductService) context.getBean("s");

		s.doSomeService();
	}

}