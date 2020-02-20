package com.kshrd.controller;

import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AboutUsController {

    @GetMapping("/about")
    public String aboutUs(){
        return "about";
    }

}
