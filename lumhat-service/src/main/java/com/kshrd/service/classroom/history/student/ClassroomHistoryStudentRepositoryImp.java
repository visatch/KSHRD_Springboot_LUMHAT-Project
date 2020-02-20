package com.kshrd.service.classroom.history.student;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomHistoryStudent;
import com.kshrd.repository.classroomRepository.ClassroomHistoryStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassroomHistoryStudentRepositoryImp implements ClassroomHistoryStudentService {
    private ClassroomHistoryStudentRepository classroomHistoryStudentRepository;

    @Autowired
    public void setClassroomHistoryStudentRepository(ClassroomHistoryStudentRepository classroomHistoryStudentRepository) {
        this.classroomHistoryStudentRepository = classroomHistoryStudentRepository;
    }

    @Override
    public List<ClassroomHistoryStudent> filterAllStudentHistoryByUserId(int userId) {
        return classroomHistoryStudentRepository.filterAllStudentHistoryByUserId(userId);
    }

    @Override
    public List<ClassroomClass> findAllEnrollClassByUserId(int userId) {
        return classroomHistoryStudentRepository.findAllEnrollClassByUserId(userId);
    }

    @Override
    public List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdByPage(int userId, Paging paging) {
        return classroomHistoryStudentRepository.filterAllStudentHistoryByUserIdByPage(userId,paging);
    }

    @Override
    public List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdAndClassId(int userId, int classId) {
        return classroomHistoryStudentRepository.filterAllStudentHistoryByUserIdAndClassId(userId,classId);
    }

    @Override
    public List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdAndClassIdByPage(int userId, int classId, Paging paging) {
        return classroomHistoryStudentRepository.filterAllStudentHistoryByUserIdAndClassIdByPage(userId,classId,paging);
    }
}
