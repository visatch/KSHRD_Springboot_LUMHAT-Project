package com.kshrd.controller;

import com.kshrd.model.*;
import com.kshrd.service.question.QuestionService;
import com.kshrd.service.quizRecord.QuizRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class QuestionController {

    private QuestionService questionService;
    private QuizRecordService quizRecordService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setQuizRecordService(QuizRecordService quizRecordService) {
        this.quizRecordService = quizRecordService;
    }


    @GetMapping("/do-quiz")
    public String question(int quiz_id, ModelMap modelMap){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user;
        try {
            user = (User) auth.getPrincipal();
        } catch (Exception e) {
            user = new User();
        }

        QuestionFilter questionFilter = new QuestionFilter(quiz_id,user.getId(),true);

        modelMap.addAttribute("instructions", questionService.findInstructionByQuizId(questionFilter));
        modelMap.addAttribute("userId", user.getId());

        return "do-quiz";
    }


    // Record history
    @PostMapping("/do-quiz")
    @ResponseBody
    public String insertRecord(@RequestBody QuizRecord quizRecord){
        quizRecordService.insert(quizRecord);
        return "do-quiz";
    }

}
