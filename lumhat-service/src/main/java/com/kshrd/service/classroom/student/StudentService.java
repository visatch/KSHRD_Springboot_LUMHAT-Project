package com.kshrd.service.classroom.student;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomHistoryStudent;
import com.kshrd.model.classroom.Student;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;


public interface StudentService {
    Student studentInfo(int user_id);
    List<ClassroomHistoryStudent> classroomHistoryStudentInfo(int userId, int classId);
    Boolean checkStudentQuizStatus(@Param("userId") int userId,@Param("quizId") int quizId);
    Boolean checkStatusClassroom(@Param("classId") int classId, @Param("userId") int userId );
    Boolean checkOldUserinClass(@Param("userId") int userId, @Param("classId") int classId);
    Boolean checkExistingQuizOldUser(@Param("userId") Integer userId, @Param("classId") Integer classId, @Param("quizId") Integer quizId);
    List<ClassroomHistoryStudent> classroomHistoryStudentInfoByPage(@Param("paging") Paging paging, @Param("userId") int userId, @Param("classId") int classId);
    void clearHistory(@Param("classId") int classId, @Param("userId")int userId);
    void insertExistingQuizNewUser(Integer userId, Integer classId);
    Boolean leaveClass(int userId,int classId);

}
