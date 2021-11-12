package com.vea.springbootnew.web;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vea.springbootnew.mapper.CategoryMapper;
import com.vea.springbootnew.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CategoryController {
    @Autowired
    CategoryMapper categoryMapper;

    @RequestMapping("/listCategory")
    public String listCategory(Model m) throws Exception {
        List<Category> cs=categoryMapper.findAll();

        m.addAttribute("cs", cs);

        return "listCategory";
    }

    @RequestMapping("/listCategoryXML")
    public String listCategoryXML(Model m) throws Exception {
        List<Category> cs=categoryMapper.findAllXML();

        m.addAttribute("cs", cs);

        return "listCategory";
    }


    @RequestMapping("/addCategory")
    public String listCategory(Category c) throws Exception {
        categoryMapper.save(c);
        return "redirect:listCategoryPage";
    }
    @RequestMapping("/deleteCategory")
    public String deleteCategory(Category c) throws Exception {
        categoryMapper.delete(c.getId());
        return "redirect:listCategoryPage";
    }
    @RequestMapping("/updateCategory")
    public String updateCategory(Category c) throws Exception {
        categoryMapper.update(c);
        return "redirect:listCategoryPage";
    }
    @RequestMapping("/editCategory")
    public String listCategory(int id,Model m) throws Exception {
        Category c= categoryMapper.get(id);
        m.addAttribute("c", c);
        return "editCategory";
    }

    @RequestMapping("/listCategoryPage")
    public String listCategory(Model m, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        //根据start,size进行分页，并且设置id 倒排序
        PageHelper.startPage(start,size,"id desc");
        List<Category> cs=categoryMapper.findAll();
        PageInfo<Category> page = new PageInfo<>(cs);
        //根据返回的集合，创建PageInfo对象
        m.addAttribute("page", page);
        return "listCategory";
    }

}