package com.kshrd.service.classroom.history.teacher;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomHistoryTeacher;
import com.kshrd.model.classroom.ClassroomResult;
import com.kshrd.repository.classroomRepository.ClassroomHistoryTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassroomHistoryTeacherServiceImp implements ClassroomHistoryTeacherService {
    private ClassroomHistoryTeacherRepository classroomHistoryTeacherRepository;



    @Autowired
    public void setClassroomHistoryTeacherRepository(ClassroomHistoryTeacherRepository classroomHistoryTeacherRepository) {
        this.classroomHistoryTeacherRepository = classroomHistoryTeacherRepository;
    }


    @Override
    public List<ClassroomHistoryTeacher> filterHistoryTeacherByClassId(int classId) {
        return classroomHistoryTeacherRepository.filterHistoryTeacherByClassId(classId);
    }

    @Override
    public List<ClassroomResult> findResultByQuizId(int quizId) {
        return classroomHistoryTeacherRepository.findResultByQuizId(quizId);
    }

    @Override
    public List<ClassroomHistoryTeacher> filterAllHistoryTeacherByClassId(int classId) {
        return classroomHistoryTeacherRepository.filterAllHistoryTeacherByClassId(classId);
    }

    @Override
    public void clearHistory(int classId) {
        classroomHistoryTeacherRepository.clearHistory(classId);
    }

    @Override
    public List<ClassroomHistoryTeacher> filterAllHistoryTeacher(int userId) {
        return classroomHistoryTeacherRepository.filterAllHistoryTeacher(userId);
    }

    @Override
    public List<ClassroomClass> findAllTeacherClassByUserId(int userId) {
        return classroomHistoryTeacherRepository.findAllTeacherClassByUserId(userId);
    }



    @Override
    public List<ClassroomHistoryTeacher> filterAllHistoryTeacherByPage(int userId, Paging paging) {
        return classroomHistoryTeacherRepository.filterAllHistoryTeacherByPage(userId,paging);
    }

    @Override
    public List<ClassroomHistoryTeacher> filterHistoryTeacherByClassIdByPage(int classId, Paging paging) {
        return classroomHistoryTeacherRepository.filterHistoryTeacherByClassIdByPage(classId,paging);
    }

    @Override
    public ClassroomClass findClassInfoByQuizId(int quizId) {
        return classroomHistoryTeacherRepository.findClassInfoByQuizId(quizId);
    }

}
