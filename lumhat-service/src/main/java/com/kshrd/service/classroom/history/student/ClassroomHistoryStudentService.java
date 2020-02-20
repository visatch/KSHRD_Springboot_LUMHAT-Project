package com.kshrd.service.classroom.history.student;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomHistoryStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomHistoryStudentService {
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserId(@Param("userId")int userId);
    List<ClassroomClass> findAllEnrollClassByUserId(@Param("userId")int userId);
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdByPage(@Param("userId")int userId, @Param("paging") Paging paging);
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdAndClassId(@Param("userId") int userId,@Param("classId")int classId);
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdAndClassIdByPage(@Param("userId") int userId,@Param("classId")int classId,@Param("paging")Paging paging);
}
