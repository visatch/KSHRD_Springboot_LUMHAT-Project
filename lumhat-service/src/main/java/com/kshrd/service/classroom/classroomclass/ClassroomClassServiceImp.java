package com.kshrd.service.classroom.classroomclass;

import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.repository.classroomRepository.ClassroomClassRepository;
import com.kshrd.repository.classroomRepository.ClassroomQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassroomClassServiceImp implements ClassroomClassService {

    private ClassroomClassRepository classroomClassRepository;
    private ClassroomQuizRepository classroomQuizRepository;

    @Autowired
    public void setClassroomClassRepository(ClassroomClassRepository classroomClassRepository){
        this.classroomClassRepository = classroomClassRepository;
    }

    @Autowired
    public void setClassroomQuizRepository(ClassroomQuizRepository classroomQuizRepository) {
        this.classroomQuizRepository = classroomQuizRepository;
    }

    @Override
    public boolean createClass(ClassroomClass classroomClass, int userId) {
        int classId = this.classroomClassRepository.createClass(classroomClass,userId);
        classroomQuizRepository.insertTopic(classId,"No Topic");
        return true;
    }

    @Override
    public boolean archiveClass(int id) {
        return this.classroomClassRepository.archiveClass(id);
    }

    @Override
    public boolean joinClass(int userId,String code) {
        return this.classroomClassRepository.joinClass(userId,code);
    }



    @Override
    public boolean updateClass(ClassroomClass classroomClass) {
        classroomClassRepository.updateClass(classroomClass);
        return true;
    }

    @Override
    public boolean deleteClass(int id) {
        return this.classroomClassRepository.deleteClass(id);
    }

    @Override
    public boolean restoreClass(int id) {
        return this.classroomClassRepository.restoreClass(id);
    }

    @Override
    public List<ClassroomClass> findAllArchivedClass(int userId) {
        return this.classroomClassRepository.findAllArchivedClass(userId);
    }

    @Override
    public Integer findClassByCode(String code) { return this.classroomClassRepository.findClassByCode(code); }

    @Override
    public List<ClassroomClass> findAllTeacherClass(int userId) {
        return classroomClassRepository.findAllTeacherClass(userId);
    }

    @Override
    public List<ClassroomClass> findAllEnrollClass(int userId) {
        return classroomClassRepository.findAllEnrollClass(userId);
    }

    @Override
    public ClassroomClass findInfoClass(int userId, int classId) {
        return classroomClassRepository.findInfoClass(userId,classId);
    }

    @Override
    public int countUserClass(int userId) {
        return classroomClassRepository.countUserClass(userId);
    }
}
