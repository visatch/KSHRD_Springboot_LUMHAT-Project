package com.kshrd.adminController;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import com.kshrd.model.UserRole;
import com.kshrd.model.form.RoleForm;
import com.kshrd.service.UserService;
import com.kshrd.service.admin.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    private AdminRoleService adminRoleService;
    @Autowired
    public void setAdminRoleService(AdminRoleService adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    @GetMapping("/userManagement")
    public String userManagement(@ModelAttribute UserRole userRole, Model m, Paging paging){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        m.addAttribute("userObj",new UserRole());
        m.addAttribute("user",userService.findOne(user.getFacebookId())) ;
        m.addAttribute("users",userService.findAllUser(paging));
        paging.setTotalCount(userService.countUser());
        List<User>users=userService.findAllUser(paging);
        m.addAttribute("paging",paging);
        return "admin/userManagement";
    }


    @GetMapping("/findAllUser")
    public String getUser(@ModelAttribute UserRole userRole,ModelMap m,Paging paging){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        m.addAttribute("userObj",new UserRole());
        m.addAttribute("user",userService.findOne(user.getFacebookId())) ;
        m.addAttribute("users",userService.findAllUser(paging));
        return "admin/userManagement::fgUser";
    }
    //Update role
    @PostMapping("/updateRole")
    public String updateRole(@RequestBody RoleForm roleForm){
        adminRoleService.updateRole(roleForm);
        return "admin/userManagement::fgUser";
    }




}
