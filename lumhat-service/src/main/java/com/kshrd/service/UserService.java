package com.kshrd.service;
import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.User;
import com.kshrd.model.UserRole;
import com.kshrd.model.form.RoleForm;

import java.util.List;

public interface UserService {
    boolean add(User user);

     User findOne(String id);

    List<User> findAll();

    boolean delete(String id);

    boolean update(User user);

     String findById(String id);

     //Counting question show on Dashboard admin
    String findQtyUser();
    String findAllQuiz();
    String findItQuestion();
    String findEnglishQuestion();
    String findKoreanQuestion();
    List<User> findAllUser(Paging paging);
    int countUser();
    int findAllClass();
    int findAllStudent();

}
