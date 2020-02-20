package com.kshrd.classroomController.studentController;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.QuestionFilter;
import com.kshrd.model.Quiz;
import com.kshrd.model.QuizRecord;
import com.kshrd.model.User;
import com.kshrd.model.classroom.*;
import com.kshrd.repository.classroomRepository.ClassroomQuestionRepository;
import com.kshrd.repository.classroomRepository.ClassroomQuizRecordRepository;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import com.kshrd.service.classroom.quiz.ClassroomQuizService;
import com.kshrd.service.classroom.student.StudentService;
import com.kshrd.service.classroom.teacher.ClassroomTeacherService;
import com.kshrd.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private StudentService studentService;
    private ClassroomQuizService classroomQuizService;
    private ClassroomTeacherService classroomTeacherService;
    private ClassroomQuestionRepository  classroomQuestionRepository;
    private ClassroomQuizRecordRepository classroomQuizRecordRepository;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setClassroomQuestionRepository(ClassroomQuestionRepository classroomQuestionRepository) {
        this.classroomQuestionRepository = classroomQuestionRepository;
    }

    @Autowired
    public void setClassroomQuizRecordRepository(ClassroomQuizRecordRepository classroomQuizRecordRepository) {
        this.classroomQuizRecordRepository = classroomQuizRecordRepository;
    }

    @Autowired
    public void setClassroomTeacherService(ClassroomTeacherService classroomTeacherService) {
        this.classroomTeacherService = classroomTeacherService;
    }

    @Autowired
    public void setClassroomQuizService(ClassroomQuizService classroomQuizService) {
        this.classroomQuizService = classroomQuizService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/classroom/student/{classId}")
    public String student(@PathVariable String classId, Model model){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            model.addAttribute("userId",userService.findOne(user.getFacebookId()).getId());
            model.addAttribute("studentinfo",studentService.studentInfo(Integer.parseInt(classId)));

            //Check status class before open classroom [status classroom 3, 0 = show, 1 = archive, 2 = deleted]
            Boolean statusClass = studentService.checkStatusClassroom(Integer.parseInt(classId),user.getId());
            if (!statusClass){
                return "redirect:/classroom";
            }
            return "/classroom/student";
        }catch (Exception e){
            return "redirect:/classroom";
        }

    }

    @GetMapping("/classroom/student/quiztab/")
    public String quizTab(@RequestParam Integer page,@RequestParam Integer classId, Model model, Paging paging){
        model.addAttribute("classid",classId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        paging.setTotalCount(classroomQuizService.findQuizStudentByClassId(classId,userService.findOne(user.getFacebookId()).getId()).size());
        paging.setPage(page);
        model.addAttribute("paging",paging);
        model.addAttribute("allQuiz",classroomQuizService.findQuizStudentByClassIdByPage(classId,userService.findOne(user.getFacebookId()).getId(),paging));
        return "/thymeleaf-ajax/student/quiz-tab :: quiz-tab";
    }

    @GetMapping("/classroom/student/stutab/{classId}")
    public String stuTab(@PathVariable Integer classId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("you",userService.findOne(user.getFacebookId()).getId());
        model.addAttribute("allStudent",classroomTeacherService.findAllStudentByClassId(classId));
        return "/thymeleaf-ajax/student/stu-tab :: stu-tab";
    }

    @GetMapping("/classroom/student/histab/")
    public String hisTab(@RequestParam Integer userId, @RequestParam Integer classId, @RequestParam Integer page, Model model, Paging paging){
        paging.setTotalCount(studentService.classroomHistoryStudentInfo(userId,classId).size());
        paging.setPage(page);
        model.addAttribute("paging",paging);
        model.addAttribute("allStuHis",studentService.classroomHistoryStudentInfoByPage(paging,userId, classId));
        return "/thymeleaf-ajax/student/his-tab :: his-tab";
    }


    @GetMapping("/classroom/do-quiz")
    public String question(@RequestParam int quiz_id, @RequestParam Integer classId, ModelMap modelMap){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user;
        try {
            user = (User) auth.getPrincipal();
        } catch (Exception e) {
            user = new User();
        }

        QuestionFilter questionFilter = new QuestionFilter(quiz_id,user.getId(),true);
        modelMap.addAttribute("classId",classId);
        modelMap.addAttribute("instructions", classroomQuestionRepository.findInstructionByQuizId(questionFilter));
        modelMap.addAttribute("userId", user.getId());

        Boolean quizStatus = studentService.checkStudentQuizStatus(user.getId(),quiz_id);

        // Function BOOLEAN check quiz status = 1 && expire date > now()
        if (!quizStatus){
            return "redirect:/classroom/student/"+classId;
        }
        return "/classroom/classroom-do-quiz";
    }

    @PostMapping("/classroom/do-quiz")
    @ResponseBody
    public String insertRecord(@RequestBody ClassroomQuizRecord classroomQuizRecord){

        classroomQuizRecordRepository.updateQuizRecord(classroomQuizRecord);
        return "classroom-do-quiz";
    }

    @PutMapping("/classroom/clear-history-student/{classId}")
    @ResponseBody
    public String clearHistoryStudent(@PathVariable int classId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        studentService.clearHistory(classId,userService.findOne(user.getFacebookId()).getId());
        return "clear";
    }

    @RequestMapping(value = {"/classroom/student/leave"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String studentLeaveClass(@RequestBody Integer classId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user;
        try {
            user = (User) auth.getPrincipal();
        } catch (Exception e) {
            user = new User();
        }

        if(studentService.leaveClass(user.getId(),classId))
            return "true";

        return "false";
    }






}
