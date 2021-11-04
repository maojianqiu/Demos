package com.how2java.pojo;

import org.springframework.stereotype.Component;

/**
 * 1.初始使用spring，创建对象，进行属性注入
 */
@Component("c")
public class Category {
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private int id;
    private String name = "Category";
}