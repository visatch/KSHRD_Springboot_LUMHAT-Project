package com.kshrd.service.classroom.teacher;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomStudent;
import com.kshrd.model.classroom.ClassroomTeacher;
import com.kshrd.repository.classroomRepository.ClassroomTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassroomTeacherServiceImp implements ClassroomTeacherService {

    private ClassroomTeacherRepository classroomTeacherRepository;

    @Autowired
    public void setClassroomTeacherRepository(ClassroomTeacherRepository classroomTeacherRepository) {
        this.classroomTeacherRepository = classroomTeacherRepository;
    }

    @Override
    public void deleteStudentFromClass(int userId, int classId) {
        classroomTeacherRepository.deleteStudentFromClass(userId,classId);
    }

    @Override
    public List<ClassroomStudent> findAllStudentByClassId(int classId) {
        return classroomTeacherRepository.findAllStudentByClassId(classId);
    }

    @Override
    public void updateCover(String imgName, int classId) {
        classroomTeacherRepository.updateCover(imgName,classId);
    }

    @Override
    public List<ClassroomTeacher> findTeacherInfo() {
        return classroomTeacherRepository.findTeacherInfo();
    }

    @Override
    public List<ClassroomTeacher> findTeacherInfoByPage(Paging paging) {
        return classroomTeacherRepository.findTeacherInfoByPage(paging);
    }


}
