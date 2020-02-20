package com.kshrd.controller;

import com.kshrd.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"","/","/home","/index"})
    public String home(ModelMap map){
        map.addAttribute("majors", categoryService.findAllMajor());
        return "home";
    }


}
