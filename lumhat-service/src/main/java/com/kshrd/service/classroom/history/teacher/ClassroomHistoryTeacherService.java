package com.kshrd.service.classroom.history.teacher;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomHistoryTeacher;
import com.kshrd.model.classroom.ClassroomResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomHistoryTeacherService {
    List<ClassroomResult> findResultByQuizId(int quizId);
    List<ClassroomHistoryTeacher> filterAllHistoryTeacherByClassId(int classId);
    void clearHistory(int classId);
    List<ClassroomHistoryTeacher> filterAllHistoryTeacher(int userId);
    List<ClassroomClass> findAllTeacherClassByUserId(@Param("userId") int userId);
    List<ClassroomHistoryTeacher> filterHistoryTeacherByClassId(@Param("classId")int classId);
    List<ClassroomHistoryTeacher> filterAllHistoryTeacherByPage(@Param("userId") int userId, @Param("paging") Paging paging);
    List<ClassroomHistoryTeacher> filterHistoryTeacherByClassIdByPage(@Param("classId")int classId,@Param("paging")Paging paging);
    ClassroomClass findClassInfoByQuizId(int quizId);
}
