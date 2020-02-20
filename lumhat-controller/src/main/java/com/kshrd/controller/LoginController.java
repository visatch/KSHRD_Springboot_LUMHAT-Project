package com.kshrd.controller;

import com.kshrd.model.User;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import com.kshrd.service.history.HistoryService;
import com.kshrd.service.quizRecord.QuizRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;


@Controller
public class LoginController {
    private UserService userService ;
    private HistoryService historyService;
    private QuizRecordService quizRecordService;
    private ClassroomClassService classroomClassService;

    @Autowired
    public void setClassroomClassService(ClassroomClassService classroomClassService) {
        this.classroomClassService = classroomClassService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Autowired
    public void setQuizRecordService(QuizRecordService quizRecordService) {
        this.quizRecordService = quizRecordService;
    }
    @GetMapping("/login")
    public String loginpage(HttpServletRequest request){
        try{
            //check if have cookie
            String userId = readCookie("userId",request).get();
            return autoLogin(userId,request);
        }catch (Exception e){
            return "login";
        }
    }

    @GetMapping(value = {"/indexLogin"})
    public String home()
    {
        return "indexLogin";
    }
    @GetMapping(value = {"/profile"})
    public String profileLog(ModelMap map){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        map.addAttribute("user",userService.findOne(user.getFacebookId())) ;
        Authentication auth1 = new UsernamePasswordAuthenticationToken(userService.findOne(user.getFacebookId()),null, user.getRoles());
        SecurityContextHolder.getContext().setAuthentication(auth1);
        map.addAttribute("histories",historyService.findHistoryByUserId(user.getId()));
        return "profile";
    }

    @PostMapping("/profile")
    public String add(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile/delete")
    public String deleteAllHistory(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (user.getId() != 0){
            quizRecordService.deleteAllRecord(user.getId());
        }
        return "redirect:/profile";
    }

    private String autoLogin(String userId,HttpServletRequest request){
        try {
            // Sign up new user and create login session
            User user = userService.findOne(userId);
            // Try to set email if user allow email access
            int  totalClass = classroomClassService.countUserClass(userService.findOne(user.getFacebookId()).getId());
            user = userService.findOne(user.getFacebookId());
            user.setTotalClass(totalClass);
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
            SecurityContextHolder.getContext().setAuthentication(auth);
            String redirectUrl = (String) request.getSession().getAttribute("Success");
            if (redirectUrl == "/error?null" || redirectUrl == null || redirectUrl == "/favicon.ico?null") {
                redirectUrl = "/profile";
            } else {
                redirectUrl = (String) request.getSession().getAttribute("Success");
            }
            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    private Optional<String> readCookie(String key, HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
