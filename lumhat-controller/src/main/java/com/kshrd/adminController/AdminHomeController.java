package com.kshrd.adminController;

import com.kshrd.model.User;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.teacher.ClassroomTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    
    private UserService userService;
    private ClassroomTeacherService classroomTeacherService;
    @Autowired
    public void setClassroomTeacherService(ClassroomTeacherService classroomTeacherService) {
        this.classroomTeacherService = classroomTeacherService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping({"","/","/dashboard"})
    public String adminHome(ModelMap map){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        map.addAttribute("allStudent",userService.findAllStudent());
        map.addAttribute("allClass",userService.findAllClass());
        map.addAttribute("allTeacher",classroomTeacherService.findTeacherInfo().size());
        map.addAttribute("allUser",userService.findQtyUser());
        map.addAttribute("allQuiz",userService.findAllQuiz());
        map.addAttribute("it",userService.findItQuestion());
        map.addAttribute("english",userService.findEnglishQuestion());
        map.addAttribute("korean",userService.findKoreanQuestion());
        map.addAttribute("user",userService.findOne(user.getFacebookId()));
        map.addAttribute("question",Integer.parseInt(userService.findItQuestion())+Integer.parseInt(userService.findEnglishQuestion())+Integer.parseInt(userService.findKoreanQuestion()));
        return "admin/index";
    }
}
