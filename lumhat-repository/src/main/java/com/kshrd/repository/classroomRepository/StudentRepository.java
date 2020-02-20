package com.kshrd.repository.classroomRepository;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomHistoryStudent;
import com.kshrd.model.classroom.Student;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository {

    @Select("SELECT * FROM get_class_by_class_id(#{class_id});")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "classname",column = "name"),
            @Result(property = "subject",column = "subject"),
            @Result(property = "room",column = "room"),
            @Result(property = "code",column = "code"),
            @Result(property = "image",column = "image"),
            @Result(property = "status",column = "status"),
            @Result(property = "students",column = "students"),
            @Result(property = "quizs",column = "quizs")
    })
    Student studentInfo(int class_id);


    @Select("Select * from get_all_student_history_by_class_userid(#{userId},#{classId})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "quizTitle",column = "title"),
            @Result(property = "fullScore",  column = "id", one = @One(select = "countQuestionByQuizId")),
    })
    List<ClassroomHistoryStudent> classroomHistoryStudentInfo(@Param("userId") int userId,@Param("classId") int classId);

    @Select("Select * from get_all_student_history_by_class_userid(#{userId},#{classId})" +
            " Limit ${paging.limit} offset ${paging.offset};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "quizTitle", column = "title"),
            @Result(property = "fullScore",  column = "id", one = @One(select = "countQuestionByQuizId")),
    })
    List<ClassroomHistoryStudent> classroomHistoryStudentInfoByPage(@Param("paging")Paging paging, @Param("userId") int userId, @Param("classId") int classId);

    @Select("Select * from check_quiz_expired_date(#{userId},#{quizId})")
    Boolean checkStudentQuizStatus(@Param("userId") int userId,@Param("quizId")  int quizId);

    @Update("update lh_quiz_record set is_delete_student = true where class_id = #{classId} and user_id = #{userId}")
    void clearHistory(@Param("classId") int classId, @Param("userId")int userId);

    @Select("select * from check_status_classroom(#{classId},#{userId});")
    Boolean checkStatusClassroom(@Param("classId") int classId, @Param("userId") int userId );

    @Update("UPDATE lh_class_detail SET status = false WHERE user_id=#{userId} AND class_id=#{classId}")
    Boolean leaveClass(@Param("userId") int userId,@Param("classId") int classId);

    @Select("select * from check_student_rejoin_class_by_userid(#{userId},#{classId})")
    Boolean checkOldUserinClass(@Param("userId") int userId, @Param("classId") int classId);

    @Select("select * from checkExistingQuizOldUser(#{userId},#{classId},#{quizId});")
    Boolean checkExistingQuizOldUser(@Param("userId") Integer userId, @Param("classId") Integer classId, @Param("quizId") Integer quizId);

    @Select("select Count(ques.id) from lh_question ques\n" +
            "            inner join lh_instruction i on ques.instruction_id = i.id\n" +
            "            inner join lh_quiz q on i.quiz_id = q.id where q.id = #{quizId};")
    Integer countQuestionByQuizId(@Param("quizId") Integer quizId);
}
