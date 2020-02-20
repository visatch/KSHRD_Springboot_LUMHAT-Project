package com.kshrd.classroomController.teacherController;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import com.kshrd.service.classroom.history.teacher.ClassroomHistoryTeacherService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class TeacherHistoryController {
    private ClassroomHistoryTeacherService classroomHistoryTeacherService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setClassroomHistoryTeacherService(ClassroomHistoryTeacherService classroomHistoryTeacherService) {
        this.classroomHistoryTeacherService = classroomHistoryTeacherService;
    }

    @GetMapping("/classroom/teaching-history")
    public String findAllHistoryByPage(@RequestParam(value = "classId",required = false) Integer classId,@Nullable @RequestParam(value = "page",required = false) Integer page, Model model, Paging paging){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        int userId = userService.findOne(user.getFacebookId()).getId();
        if(page!=null){
            if(page!=0){
                paging.setPage(page);
            }
        }
        model.addAttribute("paging",paging);
        if(classId==null){
            model.addAttribute("allClass",classroomHistoryTeacherService.findAllTeacherClassByUserId(userId));
            paging.setTotalCount(classroomHistoryTeacherService.filterAllHistoryTeacher(userId).size());
            model.addAttribute("histories", classroomHistoryTeacherService.filterAllHistoryTeacherByPage(userId,paging));
            return "/classroom/teaching_history";
        }else {
            if(classId==0){
                paging.setTotalCount(classroomHistoryTeacherService.filterAllHistoryTeacher(userId).size());
                model.addAttribute("histories", classroomHistoryTeacherService.filterAllHistoryTeacherByPage(userId,paging));
            }else {
                paging.setTotalCount(classroomHistoryTeacherService.filterHistoryTeacherByClassId(classId).size());
                model.addAttribute("histories", classroomHistoryTeacherService.filterHistoryTeacherByClassIdByPage(classId,paging));
            }
        }
        return "/thymeleaf-ajax/teacher/teaching-history-table :: historyTeacher";
    }

    @GetMapping("/classroom/find-class-by-quiz/{quizId}")
    @ResponseBody
    ClassroomClass findClassByQuizId(@PathVariable int quizId){
        return classroomHistoryTeacherService.findClassInfoByQuizId(quizId);
    }

    @PutMapping("/classroom/clear-all-class-history")
    @ResponseBody
    public String clearAllClassHistory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        int userId = userService.findOne(user.getFacebookId()).getId();
        List<ClassroomClass> classroomClasses = classroomHistoryTeacherService.findAllTeacherClassByUserId(userId);
        for(ClassroomClass classroomClass : classroomClasses){
            classroomHistoryTeacherService.clearHistory(classroomClass.getId());
        }
        return "cleared";
    }
}
