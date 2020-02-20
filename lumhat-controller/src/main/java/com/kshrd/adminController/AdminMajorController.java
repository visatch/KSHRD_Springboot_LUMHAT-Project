package com.kshrd.adminController;

import com.kshrd.model.Major;
import com.kshrd.model.SubMajor;
import com.kshrd.model.Subject;
import com.kshrd.model.User;
import com.kshrd.model.form.SubMajorForm;
import com.kshrd.model.form.SubjectForm;
import com.kshrd.service.UserService;
import com.kshrd.service.admin.major.AdminMajorService;
import com.kshrd.service.admin.sub_major.AdminSubMajorService;
import com.kshrd.service.admin.subject.AdminSubjectService;
import com.kshrd.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminMajorController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private AdminMajorService majorService;

    @Autowired
    public void setAdminMajorService(AdminMajorService majorService) {
        this.majorService = majorService;
    }

    private AdminSubMajorService subMajorService;

    @Autowired
    public void setSubMajorService(AdminSubMajorService subMajorService) {
        this.subMajorService = subMajorService;
    }

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private AdminSubjectService adminSubjectService;

    @Autowired
    public void setAdminSubjectService(AdminSubjectService adminSubjectService) {
        this.adminSubjectService = adminSubjectService;
    }

    @GetMapping("/major")
    public String major(Model m) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        m.addAttribute("user", userService.findOne(user.getFacebookId()));

        m.addAttribute("major", new Major());
        m.addAttribute("majors", majorService.findAll());

        m.addAttribute("subMajor", new SubMajor());
        m.addAttribute("subMajors", categoryService.findAllSubMajor());

        m.addAttribute("subject", new Subject());
        m.addAttribute("subjects", adminSubjectService.findAllSubject());
        return "admin/major";
    }
//    Major Function. Request with AJAX using fragment

    @GetMapping("/major/findAll")
    public String showMajor(Model m) {
        m.addAttribute("majors", majorService.findAll());
        return "admin/major::tbMajor";
    }

    @GetMapping("/cbMajor/findAll")
    public String comboxMajor(Model m){
        m.addAttribute("majors", majorService.findAll());
        return "admin/major::fragmentComboMajor";
    }
    @GetMapping("/cbSubMajor/findAll")
    public String comboxSubMajor(Model m){
        m.addAttribute("subMajors", categoryService.findAllSubMajor());
        return "admin/major::fragmentComboSubMajor";
    }

    @PostMapping("/addMajor")
    public String addMajor(@RequestBody Major major, Model m) {
        m.addAttribute("majors", majorService.findAll());
        majorService.add(major);
        return "admin/major::tbMajor";
    }

    @PostMapping("/updateMajor")
    public String updateMajor(@RequestBody Major major, Model m) {
        m.addAttribute("major", major);
        majorService.update(major);
        System.out.println(major);
        return "/admin/major::tbMajor";
    }

    @DeleteMapping("/deleteMajor/{id}")
    public @ResponseBody
    String deleteMajor(@PathVariable("id") int id) {
        majorService.delete(id);
        return "redirect:/major/findAll";
    }

    //    subMajor----------------------------------------------------------
    @GetMapping("/subMajor/findAll")
    public String showSubMajor(Model m) {
        m.addAttribute("subMajors", categoryService.findAllSubMajor());
        return "admin/major::tblSubMajor";
    }

    @PostMapping("/addSubMajor")
    public String addSubMajor(@RequestBody SubMajorForm subMajor) {
        subMajorService.add(subMajor);
        return "admin/major::tblSubMajor";
    }

    @PostMapping("/updateSubMajor")
    public String updateSubMajor(@RequestBody SubMajorForm subMajorForm, Model m) {
        m.addAttribute("subMajor", subMajorForm);
        subMajorService.update(subMajorForm);
        System.out.println(subMajorForm);
        return "redirect:/admin/subMajor/findAll";
    }

    @DeleteMapping("/deleteSubMajor/{id}")
    public @ResponseBody
    String deleteSubMajor(@PathVariable("id") int id) {
        subMajorService.delete(id);
        return "redirect:/subMajor/findAll";
    }

    //  subject---------------------------------------------
    @GetMapping("/subject/findAll")
    public String showSubject(Model m) {
        m.addAttribute("subjects", adminSubjectService.findAllSubject());
        return "admin/major::tblSubject";
    }

    @PostMapping("/addSubject")
    public String saveSubject(@RequestBody SubjectForm subjectForm) {
        adminSubjectService.add(subjectForm);
        System.out.println(subjectForm);
        return "admin/major::tblSubject";
    }

    @DeleteMapping("/deleteSubject/{id}")
    public @ResponseBody
    String deleteSubject(@PathVariable("id") int id) {
        adminSubjectService.delete(id);
        return "redirect:/subject/findAll";
    }

    @PostMapping("/updateSubject")
    public String updateSubject(@RequestBody SubjectForm subjectForm, Model m) {
        m.addAttribute("subject", subjectForm);
        adminSubjectService.update(subjectForm);
        System.out.println(subjectForm);
        return "redirect:/admin/subject/findAll";
    }

}
