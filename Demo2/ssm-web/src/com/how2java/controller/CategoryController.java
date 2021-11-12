package com.how2java.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.how2java.pojo.Category;
import com.how2java.service.CategoryService;

import javax.servlet.http.HttpServletRequest;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("listCategory")
	public ModelAndView listCategory(){
		ModelAndView mav = new ModelAndView();
		List<Category> cs= categoryService.list();
		
		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;
	}

	@RequestMapping("servletContext")
	@ResponseBody
	public String servletContext(HttpServletRequest httpServletRequest){
		System.out.println(httpServletRequest.getServletContext().getAttribute("contextConfigLocation"));
		System.out.println(httpServletRequest.getServletContext().getInitParameter("contextConfigLocation"));
		return "mav";
	}

}
