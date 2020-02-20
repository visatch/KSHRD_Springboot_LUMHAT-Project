package com.kshrd.classroomController.studentController;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.history.student.ClassroomHistoryStudentService;
import com.kshrd.service.classroom.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentHistoryController {

    private ClassroomHistoryStudentService classroomHistoryStudentService;
    private StudentService studentService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setClassroomHistoryStudentService(ClassroomHistoryStudentService classroomHistoryStudentService) {
        this.classroomHistoryStudentService = classroomHistoryStudentService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/classroom/student-history")
    public String filterAllStudentHistoryByClass(@RequestParam(value = "classId",required = false) Integer classId,@RequestParam(value = "page",required = false) Integer page, Model model, Paging paging){
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
            System.out.println(123);
            paging.setTotalCount(classroomHistoryStudentService.filterAllStudentHistoryByUserId(userId).size());
            model.addAttribute("AllEnrollClass",classroomHistoryStudentService.findAllEnrollClassByUserId(userId));
            model.addAttribute("allStuHis",classroomHistoryStudentService.filterAllStudentHistoryByUserIdByPage(userId,paging));
            return "/classroom/enrolled_history";
        }else {
            if(classId==0){
                paging.setTotalCount(classroomHistoryStudentService.filterAllStudentHistoryByUserId(userId).size());
                model.addAttribute("allStuHis",classroomHistoryStudentService.filterAllStudentHistoryByUserIdByPage(userId,paging));
            }else {
                paging.setTotalCount(classroomHistoryStudentService.filterAllStudentHistoryByUserIdAndClassId(userId,classId).size());
                model.addAttribute("allStuHis",classroomHistoryStudentService.filterAllStudentHistoryByUserIdAndClassIdByPage(userId,classId,paging));
            }
        }
        return "/thymeleaf-ajax/student/student-history-table :: historyStudent";
    }

    @PutMapping("/classroom/clear-all-enroll-history")
    @ResponseBody
    public String clearAllEnrollHistory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        int userId = userService.findOne(user.getFacebookId()).getId();
        List<ClassroomClass> classroomClasses = classroomHistoryStudentService.findAllEnrollClassByUserId(userId);
        for (ClassroomClass classroomClass : classroomClasses){
            studentService.clearHistory(classroomClass.getId(),userId);
        }
        return "cleared";
    }

}
