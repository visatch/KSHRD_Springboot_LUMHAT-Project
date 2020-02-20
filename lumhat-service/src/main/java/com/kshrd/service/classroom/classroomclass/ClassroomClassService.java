package com.kshrd.service.classroom.classroomclass;

import com.kshrd.model.classroom.ClassroomClass;

import java.util.List;

public interface ClassroomClassService {
    boolean createClass(ClassroomClass classroomClass, int userId);
    boolean archiveClass(int id);
    boolean joinClass(int userId,String code);
    boolean updateClass(ClassroomClass classroomClass);
    boolean deleteClass(int id);
    boolean restoreClass(int id);
    List<ClassroomClass> findAllArchivedClass(int userId);
    Integer findClassByCode(String code);
    List<ClassroomClass> findAllTeacherClass(int userId);
    List<ClassroomClass> findAllEnrollClass(int userId);
    ClassroomClass findInfoClass(int userId, int classId);
    int countUserClass(int userId);
}
