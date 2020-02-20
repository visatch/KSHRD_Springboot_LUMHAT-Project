package com.kshrd.service;
import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import com.kshrd.model.UserRole;
import com.kshrd.model.form.RoleForm;
import com.kshrd.repository.RoleRepository;
import com.kshrd.repository.UserRepository;
import com.kshrd.service.admin.AdminRoleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@MapperScan("com.kshrd.app.repository")
public class UserServiceImp  implements UserService  {

    private UserRepository userRepo;
    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean add(User user)
    {
        return userRepo.add(user);
    }

    @Override
    public User findOne(String id)
    {
        return userRepo.findOne(id);
    }

    @Override
    public List<User> findAll()
    {
        return userRepo.findAll();
    }

    @Override
    public boolean delete(String id)
    {
        return userRepo.delete(id);
    }

    @Override
    public boolean update(User user)
    {
        return userRepo.update(user);
    }

    @Override
    public String findById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public String findQtyUser() {
        return userRepo.findQtyUser();
    }

    @Override
    public String findAllQuiz() {
        return userRepo.findAllQuiz();
    }

    @Override
    public String findItQuestion() {
        return userRepo.findItQuestion();
    }

    @Override
    public String findEnglishQuestion() {
        return userRepo.findEnglishQuestion();
    }

    @Override
    public String findKoreanQuestion() {
        return userRepo.findKoreanQuestion();
    }

    @Override
    public List<User> findAllUser(Paging paging) {
        return userRepo.findAllUser(paging);
    }

    @Override
    public int countUser() {
        return userRepo.countUser();
    }

    @Override
    public int findAllClass() {
        return userRepo.findAllClass();
    }

    @Override
    public int findAllStudent() {
        return userRepo.findAllStudent();
    }
}
