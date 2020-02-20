package com.kshrd.controller;

import com.kshrd.model.Quiz;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import com.kshrd.service.quiz.QuizService;
import com.kshrd.service.category.CategoryService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    private CategoryService categoryService;
    private QuizService quizService;

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    Get Major,subMajor and Subject
    @GetMapping("/category")
    public String category(int major_id, Integer sub_major_id, ModelMap modelMap){
        modelMap.addAttribute("major", categoryService.findMajorById(major_id));
        modelMap.addAttribute("subMajorId", sub_major_id);
        if(sub_major_id != null){
            List<Quiz>list = quizService.findQuizById(sub_major_id);
            if (list.isEmpty()){
                modelMap.addAttribute("quizzes",0);
            }else {
                modelMap.addAttribute("level", quizService.findLevelByMajorId(sub_major_id));
                modelMap.addAttribute("quizzes", list);
            }
        }
        return "category";
    }

}
