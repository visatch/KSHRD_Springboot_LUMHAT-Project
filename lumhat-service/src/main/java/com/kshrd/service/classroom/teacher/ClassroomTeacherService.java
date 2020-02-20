package com.kshrd.service.classroom.teacher;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomStudent;
import com.kshrd.model.classroom.ClassroomTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomTeacherService {
    void deleteStudentFromClass(int userId, int classId);
    List<ClassroomStudent> findAllStudentByClassId(int classId);
    void updateCover(@Param("imgName") String imgName, @Param("classId") int classId);
    List<ClassroomTeacher> findTeacherInfo();
    List<ClassroomTeacher> findTeacherInfoByPage(Paging paging);
}
