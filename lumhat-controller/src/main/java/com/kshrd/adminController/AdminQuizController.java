package com.kshrd.adminController;

import com.kshrd.model.*;
import com.kshrd.service.UserService;
import com.kshrd.service.admin.quiz.AdminQuizService;
import com.kshrd.service.category.CategoryService;
import com.kshrd.service.question.QuestionService;
import com.kshrd.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminQuizController {

    private CategoryService categoryService;

    private QuizService quizService;

    private AdminQuizService adminQuizService;

    private UserService userService;

    private QuestionService questionService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMajorService(CategoryService majorService) {
        this.categoryService = majorService;
    }

    @Autowired
    public void setQuizService(QuizService quizService){
        this.quizService=quizService;
    }

    @Autowired
    public void setAdminQuizService(AdminQuizService adminQuizService) {
        this.adminQuizService = adminQuizService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }



    @GetMapping("/quiz")
    public String quiz(ModelMap addQuiz){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        addQuiz.addAttribute("user",userService.findOne(user.getFacebookId())) ;
//        This variable is used to store all majors, sub-majors, subjects
        List<Major> majors= categoryService.findAllMajor();


        for(int i=0;i<majors.size();i++){
            for(int j=0;j<majors.get(i).getSubMajors().size();j++){
                for(int k=0;k<majors.get(i).getSubMajors().get(j).getSubjects().size();k++){
                    majors.get(i).getSubMajors().get(j).getSubjects().get(k).setQuizList(quizService.findQuizById(majors.get(i).getSubMajors().get(j).getSubjects().get(k).getId()));
                }
            }
        }

        addQuiz.addAttribute("majors",majors);
        addQuiz.addAttribute("question",new Question());

        QuestionFilter questionFilter=new QuestionFilter(12,9,false);
        List<Instruction> instructions=new ArrayList<>();
        addQuiz.addAttribute("instructions",instructions);

        addQuiz.addAttribute("quizView",new Quiz());
        return "admin/quiz";
    }


    @PostMapping("/add-question")
    @ResponseBody
    public void addQuestion(@RequestBody Question question){
        System.out.println(question);
    }

    //add quiz, instructions, questions, and answers to database
    @PostMapping("/add-questions")
    @ResponseBody
    public String addQuestions(@RequestBody Quiz quiz){
        return (adminQuizService.insertAllDataIntoDatabase(quiz)? "quiz" : "error") ;
    }

    @GetMapping("/quiz/view-quiz/{id}")
    public String viewQuiz(@PathVariable int id,ModelMap model){

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        User user;

        try{
            user=(User)auth.getPrincipal();
        }catch (Exception e){
            user=new User();
        }

        QuestionFilter questionFilter=new QuestionFilter(id,user.getId(),false);

        Quiz quiz=adminQuizService.findQuizById(id);

        List<Instruction> instructions= questionService.findInstructionByQuizId(questionFilter);

        model.addAttribute("quizView",quiz);
        model.addAttribute("instructions",instructions);
        return "admin/quiz::viewQuiz";
    }

    @GetMapping("/quiz/view-quiz/delete-question/{quizID}/{questionID}")
    public String deleteQuestionByID(@PathVariable int quizID, @PathVariable int questionID,ModelMap model){
        adminQuizService.deleteQuestionByQuestionID(questionID);
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        User user;

        try{
            user=(User)auth.getPrincipal();
        }catch (Exception e){
            user=new User();
        }

        Quiz quiz=adminQuizService.findQuizById(quizID);
        QuestionFilter questionFilter=new QuestionFilter(quizID,user.getId(),false);
        List<Instruction> instructions = questionService.findInstructionByQuizId(questionFilter);

        model.addAttribute("quizView",quiz);
        model.addAttribute("instructions",instructions);
        return "admin/quiz::viewQuiz";
    }

    @PostMapping("/quiz/view-quiz/delete-quiz/{quizID}")
    @ResponseBody
    public String deleteQuizByID(@PathVariable int quizID){

        return (adminQuizService.deleteQuizByID(quizID)?"quiz":"error");
    }

    @PostMapping("/quiz/view-quiz/update-question/{quizID}")
    public String updateQuestonByID(@RequestBody Question question,@PathVariable int quizID){
        adminQuizService.updateQuestionByID(question);
        return "redirect:/admin/quiz/view-quiz/"+quizID;
    }

    @PostMapping("/quiz/view-quiz/insert-question/{quizID}")
    public String insertQuestion(@RequestBody Question question,@PathVariable int quizID){


        adminQuizService.insertQuestion(question);

        for (int k = 0; k < question.getAnswers().size(); k++) {
            if (!question.getAnswers().get(k).getOption().equals("")) {
                question.getAnswers().get(k).setQuestion(question);
                //add answer
                adminQuizService.insertAnswer(question.getAnswers().get(k));
            }
        }

        return "redirect:/admin/quiz/view-quiz/"+quizID;
    }




}
