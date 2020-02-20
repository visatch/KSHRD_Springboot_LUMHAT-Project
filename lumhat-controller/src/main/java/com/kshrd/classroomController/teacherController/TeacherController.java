package com.kshrd.classroomController.teacherController;

import com.kshrd.configuration.utility.DateExpire;
import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import com.kshrd.model.classroom.*;
import com.kshrd.service.UserService;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import com.kshrd.service.classroom.history.teacher.ClassroomHistoryTeacherService;
import com.kshrd.service.classroom.quiz.ClassroomQuizService;
import com.kshrd.service.classroom.teacher.ClassroomTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.xml.bind.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
public class TeacherController {

    @Value("${file.image.client}")
    String client;
    private ClassroomQuizService classroomQuizService;
    private ClassroomHistoryTeacherService classroomHistoryTeacherService;
    private UserService userService;
    private ClassroomTeacherService classroomTeacherService;
    private ClassroomClassService classroomClassService;

    @Autowired
    public void setClassroomQuizService(ClassroomQuizService classroomQuizService) {
        this.classroomQuizService = classroomQuizService;
    }

    @Autowired
    public void setClassroomHistoryTeacherService(ClassroomHistoryTeacherService classroomHistoryTeacherService) {
        this.classroomHistoryTeacherService = classroomHistoryTeacherService;
    }

    @Autowired
    public void setClassroomTeacherService(ClassroomTeacherService classroomTeacherService) {
        this.classroomTeacherService = classroomTeacherService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setClassroomClassService(ClassroomClassService classroomClassService) {
        this.classroomClassService = classroomClassService;
    }

    @GetMapping("/classroom/teacher/{classId}")
    public String teacher(Model model,@PathVariable String classId){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            int userId = userService.findOne(user.getFacebookId()).getId();
            if(classroomClassService.findInfoClass(userId,Integer.valueOf(classId))==null){
                return "redirect:/classroom";
            }
            model.addAttribute("topics",classroomQuizService.findAllTopicByClassId(Integer.valueOf(classId)));
            model.addAttribute("classInfo",classroomClassService.findInfoClass(userId,Integer.valueOf(classId)));
            return "/classroom/teaching";
        }catch (Exception e){
            return "redirect:/classroom";
        }
    }

    @PostMapping("/classroom/add-questions")
    @ResponseBody
    public String addQuestions(@RequestBody ClassroomQuiz quiz){
        return (classroomQuizService.insertAllDataIntoDatabase(quiz)? "quiz" : "error") ;
    }

    @DeleteMapping("/classroom/delete-quiz/{quizId}")
    @ResponseBody
    public String removeQuiz(@PathVariable int quizId){
        classroomQuizService.deleteQuizByID(quizId);
        return "success";
    }

    @PostMapping("/classroom/delete-user/{classId}")
    @ResponseBody
    public String removeStudent(@RequestBody List<Integer> removeId,@PathVariable int classId){
        for (Integer id: removeId) {
            classroomTeacherService.deleteStudentFromClass(id,classId);
        }
        return ""+classroomTeacherService.findAllStudentByClassId(classId).size();
    }

    @GetMapping("/classroom/result-quiz/{quizId}")
    @ResponseBody
    List<ClassroomResult> resultQuiz(@PathVariable int quizId){
        return classroomHistoryTeacherService.findResultByQuizId(quizId);
    }

    @GetMapping("/classroom/student-tab/{classId}")
    public String studentTab(@PathVariable int classId, Model model){
        model.addAttribute("allStudent",classroomTeacherService.findAllStudentByClassId(classId));
        return "/thymeleaf-ajax/teacher/student-tab :: studentTab";
    }

    @GetMapping("/classroom/quiz/")
    public String quizTab(@RequestParam Integer page,@RequestParam Integer classId, Model model, Paging paging){
        paging.setTotalCount(classroomQuizService.findAllQuizByClassId(classId).size());
        paging.setPage(page);
        model.addAttribute("paging",paging);
        model.addAttribute("allQuiz",classroomQuizService.findAllQuizByClassIdByPage(classId,paging));
        return "/thymeleaf-ajax/teacher/quiz-tab :: quizTab";
    }

    @GetMapping("/classroom/history/")
    public String historyTab(@RequestParam String page,@RequestParam String classId, Model model, Paging paging){
        paging.setTotalCount(classroomHistoryTeacherService.filterAllHistoryTeacherByClassId(Integer.parseInt(classId)).size());
        paging.setPage(Integer.parseInt(page));
        model.addAttribute("paging",paging);
        model.addAttribute("histories",classroomHistoryTeacherService.filterHistoryTeacherByClassIdByPage(Integer.parseInt(classId),paging));
        return "/thymeleaf-ajax/teacher/history-tab :: historyTab";
    }

    @GetMapping("/classroom/create-quiz/{classId}")
    public String createQuizModal(@PathVariable int classId, Model model){
        model.addAttribute("topics",classroomQuizService.findAllTopicByClassId(classId));
        return "/thymeleaf-ajax/teacher/create-quiz :: createQuiz";
    }

    @GetMapping("/classroom/preview-quiz")
    public String previewQuiz(@RequestParam("quizId") int quizId, Model model){
        model.addAttribute("quiz",classroomQuizService.findQuizById(false,quizId));
        return "/thymeleaf-ajax/teacher/preview-quiz :: previewQuiz";
    }

    @GetMapping("/classroom/edit-quiz/")
    @ResponseBody
    ClassroomQuiz editQuiz(@RequestParam(value = "isDraft",required = false)  Boolean isDraft, @RequestParam int quizId){
        if(isDraft==null){
            return classroomQuizService.findQuizById(null,quizId);
        }
        return classroomQuizService.findQuizById(isDraft,quizId);
    }

    @GetMapping("/classroom/get-all-class")
    @ResponseBody
    List<ClassroomClass> getAllClass(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return classroomClassService.findAllTeacherClass(userService.findOne(user.getFacebookId()).getId());
    }

    @GetMapping("/classroom/get-all-topic/{classId}")
    @ResponseBody
    List<ClassroomQuizTopic> getAllQuizByClassId(@PathVariable int classId){
        return classroomQuizService.findAllQuizByTopicId(classId);
    }

    @PostMapping("/classroom/edit-questions")
    @ResponseBody
    public String editQuestions(@RequestBody ClassroomQuiz quiz){
        if(!quiz.isDraft){
            quiz.setDateExpire(DateExpire.getDateExpire(quiz.getHourExpire()));
        }
        classroomQuizService.removeQuizFromDbById(quiz.getId());
        classroomQuizService.insertAllDataIntoDatabase(quiz);
        return "success";
    }

    @GetMapping("/classroom/publish-quiz/")
    @ResponseBody
    public String publishQuiz(@RequestParam Integer quizId,@RequestParam Integer duration,@RequestParam Integer hourExpire){
        Date dateExpire = DateExpire.getDateExpire(hourExpire);
        classroomQuizService.publishQuiz(quizId,duration*60,dateExpire);
        return "published";
    }

    @GetMapping("/classroom/archive-class/{classId}")
    @ResponseBody
    public String archiveClass(@PathVariable int classId){
        classroomClassService.archiveClass(classId);
        return "archived";
    }

    @PostMapping("/classroom/update-class")
    @ResponseBody
    public ClassroomClass updateClass(@RequestBody ClassroomClass classroomClass){
        classroomClassService.updateClass(classroomClass);
        return classroomClass;
    }

    @PutMapping("/classroom/clear-history-class/{classId}")
    @ResponseBody
    public String clearHistoryClass(@PathVariable int classId){
        classroomHistoryTeacherService.clearHistory(classId);
        return "clear";
    }

    @PostMapping("/classroom/change-cover-class/{classId}")
    @ResponseBody
    public String changeCoverClass(@PathVariable("classId")int classId,@RequestBody String img){
        try{
            String base64Image = img.split(",")[1];
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);
            String imgName = UUID.randomUUID().toString()+".png";
            classroomTeacherService.updateCover(imgName,classId);
            File outputFile = new File(client+imgName);
            ImageIO.write(image, "png",outputFile);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "changed";
    }
}
