package com.kshrd.classroomController;

import com.kshrd.configuration.utility.RandomImage;
import com.kshrd.model.User;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomQuiz;
import com.kshrd.model.classroom.ClassroomQuizTopic;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import com.kshrd.service.classroom.quiz.ClassroomQuizService;
import com.kshrd.service.classroom.student.StudentService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ClassroomClassController {

    private ClassroomClassService classroomClassService;
    private StudentService studentService;
    private UserService userService;
    private ClassroomQuizService classroomQuizService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setClassroomQuizService(ClassroomQuizService classroomQuizService) {
        this.classroomQuizService = classroomQuizService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setClassroomClassService(ClassroomClassService classroomClassService){
        this.classroomClassService = classroomClassService;
    }

    // get current user id
    private int getCurrentUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        try {
            user = (User) auth.getPrincipal();
        } catch (Exception e) {
            user = new User();
        }
        return userService.findOne(user.getFacebookId()).getId();
    }

    // add user to model
    private void addUserToModel(ModelMap map){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user;
        try {
            user = (User) auth.getPrincipal();
        } catch (Exception e) {
            user = new User();
        }

        map.addAttribute("user",user);
    }

    @RequestMapping(value = {"/classroom/create"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String classroomCreateClass(
            @RequestParam("name") String name,
            @RequestParam("subject") String subject,
            @RequestParam("room") String room){
        ClassroomClass classroomClass = new ClassroomClass(name,subject,room);
        classroomClass.setImagePath(RandomImage.getRandomImage());
        return classroomClassService.createClass(classroomClass,getCurrentUserId())?"true":"false";
    }

    @RequestMapping(value = {"/classroom/join"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String classroomJoinClass(@RequestParam("code") String code){
        Boolean checkOldUserInClass;
        Integer classId = classroomClassService.findClassByCode(code);
        if (classId!=null)
            checkOldUserInClass = studentService.checkOldUserinClass(getCurrentUserId(),classId);
        else
            return "0";

        if(classroomClassService.joinClass(getCurrentUserId(),code)){
            if (checkOldUserInClass){
                List<ClassroomQuizTopic> classroomQuizTopicList = classroomQuizService.findAllQuizByTopicId(classId);
                for (ClassroomQuizTopic allQuiz:classroomQuizTopicList) {
                    List<ClassroomQuiz> quizzes = allQuiz.getQuizzes();
                    for (ClassroomQuiz quiz:quizzes) {
                        studentService.checkExistingQuizOldUser(getCurrentUserId(),classId,quiz.getId());
                    }
                }
                return String.valueOf(classId);
            }else {
                studentService.insertExistingQuizNewUser(getCurrentUserId(),classId);
                return String.valueOf(classId);
            }
        }

        return "0";
    }

    @GetMapping("/classroom/archived")
    public String classroomArchivedClass(ModelMap map){
        addUserToModel(map);
        map.addAttribute("no_more_archive_class",true);
        map.addAttribute("no_archive_class",true);

        if(this.classroomClassService.findAllArchivedClass(getCurrentUserId()).size()>0){
            map.addAttribute("no_archive_class",false);
            map.addAttribute("ClassroomClass",this.classroomClassService.findAllArchivedClass(getCurrentUserId()));
        }

        if(this.classroomClassService.findAllArchivedClass(getCurrentUserId()).size()>0)
            map.addAttribute("no_more_archive_class",false);

        return "classroom/archived";
    }

    // more archived classes
    @RequestMapping(value = "/classroom/archived/more", produces = "application/json",method = RequestMethod.GET)
    @ResponseBody
    public List<ClassroomClass> classroomMoreArchivedClasses(@RequestParam("page") Integer morePage, ModelMap map){
        map.addAttribute("no_more_archive_class",true);
        if(this.classroomClassService.findAllArchivedClass(getCurrentUserId()).size()>0){
            map.addAttribute("no_more_archive_class",false);
            return this.classroomClassService.findAllArchivedClass(getCurrentUserId());
        }
        return null;
    }

    // remove archived class
    @RequestMapping(value = "/classroom/archived/remove", produces = "application/json" ,method = RequestMethod.GET)
    @ResponseBody
    public List<ClassroomClass> classroomRemoveArchived(@RequestParam("id") Integer id){
        if(this.classroomClassService.deleteClass(id))
            return this.classroomClassService.findAllArchivedClass(getCurrentUserId());
        return null;
    }

    // restore archived class
    @RequestMapping(value = "/classroom/archived/restore", produces = "application/json" ,method = RequestMethod.GET)
    @ResponseBody
    public List<ClassroomClass> classroomRestoreArchived(@RequestParam("id") Integer id){
        if(this.classroomClassService.restoreClass(id))
            return this.classroomClassService.findAllArchivedClass(getCurrentUserId());
        return null;
    }

    // enrolled class history
    @GetMapping("/classroom/history/enrolled")
    public String classroomEnrolledHistory(ModelMap map){
        return "classroom/enrolled_history";
    }

    // teaching class history
    @GetMapping("/classroom/history/teaching")
    public String classroomTeachingHistory(ModelMap map){
        return "classroom/teaching_history";
    }


}
