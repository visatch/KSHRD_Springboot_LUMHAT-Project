package com.kshrd.classroomController;

import com.kshrd.model.User;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClassroomHomeController {

    private UserService userService;
    private ClassroomClassService classroomClassService;

    @Autowired
    public void setClassroomClassService(ClassroomClassService classroomClassService) {
        this.classroomClassService = classroomClassService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/classroom")
    public String profileLog(ModelMap map){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<ClassroomClass> allTeacherClass = classroomClassService.findAllTeacherClass(userService.findOne(user.getFacebookId()).getId());
        List<ClassroomClass> allEnrollClass = classroomClassService.findAllEnrollClass(userService.findOne(user.getFacebookId()).getId());
        map.put("profile",user.getImageUrl());
        //Find all class that user have
        int  totalClass = classroomClassService.countUserClass(userService.findOne(user.getFacebookId()).getId());
        user = userService.findOne(user.getFacebookId());
        user.setTotalClass(totalClass);
        //Set new credential to user
        Authentication auth1 = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
        SecurityContextHolder.getContext().setAuthentication(auth1);
        if(allTeacherClass.size()==0&&allEnrollClass.size()==0){
            return "/classroom/noclass-noenroll";
        }else {
            map.addAttribute("archive",allTeacherClass.size());
            map.addAttribute("allTeacherClass",allTeacherClass);
            map.addAttribute("allEnrollClass",allEnrollClass);
            return "/classroom/index";
        }

    }

    @GetMapping("/classroom/enrolled")
    public String classroomEnrolled(){
        return "/classroom/enrolled";
    }

    @GetMapping("/classroom/teaching")
    public String classroomTeaching(){
        return "/classroom/teaching";
    }

    @GetMapping("/classroom/enrolled_history")
    public String classroomEnrolledHistory(){
        return "/classroom/enrolled_history";
    }

    @GetMapping("/classroom/teaching_history")
    public String classroomTeachingHistory(){
        return "/classroom/teaching_history";
    }
}
