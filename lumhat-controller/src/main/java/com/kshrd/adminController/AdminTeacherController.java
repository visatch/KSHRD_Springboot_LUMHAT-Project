package com.kshrd.adminController;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import com.kshrd.model.UserRole;
import com.kshrd.model.classroom.ClassroomTeacher;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.teacher.ClassroomTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminTeacherController {
    private ClassroomTeacherService classroomTeacherService;
    private UserService userService;

    @Autowired
    public void setClassroomTeacherService(ClassroomTeacherService classroomTeacherService) {
        this.classroomTeacherService = classroomTeacherService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/admin/teacher")
    public String teacher(Model m, Paging paging){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<ClassroomTeacher> teachers = classroomTeacherService.findTeacherInfo();
        paging.setTotalCount(teachers.size());
        m.addAttribute("userObj",new UserRole());
        m.addAttribute("user",userService.findOne(user.getFacebookId())) ;
        m.addAttribute("teachers",classroomTeacherService.findTeacherInfoByPage(paging));
        m.addAttribute("paging",paging);
        return "/admin/teacher";
    }
}
