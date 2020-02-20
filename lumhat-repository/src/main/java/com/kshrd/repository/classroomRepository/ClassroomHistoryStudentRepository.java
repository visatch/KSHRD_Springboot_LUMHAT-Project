package com.kshrd.repository.classroomRepository;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomHistoryStudent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassroomHistoryStudentRepository {

    @Select("SELECT lq.id,lq.title,lqr.duration, lqr.record_date,lqr.score,lqr.status from lh_quiz lq\n" +
            "            inner join lh_quiz_record lqr on lq.id = lqr.quiz_id\n" +
            "            where ( (lqr.class_id!=0) and (lqr.user_id=#{userId}) and (lq.status=true) " +
            "            and ( lqr.status = 0 or (lqr.status = 1 and lq.date_expire<now()) ) and lqr.is_delete_student = false and lq.draft = false) order by lqr.record_date desc\n"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "quizTitle", column = "title"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "fullScore",  column = "id", one = @One(select = "countQuestionByQuizId")),
    })
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserId(@Param("userId")int userId);

    @Select("SELECT lq.id,lq.title,lqr.duration, lqr.record_date,lqr.score,lqr.status from lh_quiz lq\n" +
            "            inner join lh_quiz_record lqr on lq.id = lqr.quiz_id\n" +
            "            where ( (lqr.class_id!=0) and (lqr.user_id=#{userId}) and (lq.status=true) " +
            "            and ( lqr.status = 0 or (lqr.status = 1 and lq.date_expire<now()) ) and lqr.is_delete_student = false and lq.draft = false) order by lqr.record_date desc\n" +
            "            limit #{paging.limit} offset #{paging.offset};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "quizTitle", column = "title"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "fullScore",  column = "id", one = @One(select = "countQuestionByQuizId")),
    })
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdByPage(@Param("userId")int userId, @Param("paging")Paging paging);

    @Select("select Count(ques.id) from lh_question ques\n" +
            "            inner join lh_instruction i on ques.instruction_id = i.id\n" +
            "            inner join lh_quiz q on i.quiz_id = q.id where q.id = #{quizId};")
    Integer countQuestionByQuizId(@Param("quizId") Integer quizId);

    @Select("select * from get_all_enroll_class_by_user_id(#{userId})")
    @Results({
            @Result(column = "image_path", property = "imagePath"),
            @Result(column = "students", property = "totalStudent"),
            @Result(column = "quizs", property = "totalQuiz"),
            @Result(column = "created_date", property = "createdDate")
    })
    List<ClassroomClass> findAllEnrollClassByUserId(@Param("userId")int userId);

    @Select("SELECT lq.id,lq.title,lqr.duration, lqr.record_date,lqr.score,lqr.status from lh_quiz lq\n" +
            "            inner join lh_quiz_record lqr on lq.id = lqr.quiz_id\n" +
            "            where ( (lqr.class_id=#{classId}) and (lqr.user_id=#{userId}) and (lq.status=true) " +
            "            and ( lqr.status = 0 or (lqr.status = 1 and lq.date_expire<now()) ) and lqr.is_delete_student = false and lq.draft = false) order by lqr.record_date desc\n"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "quizTitle", column = "title"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "fullScore",  column = "id", one = @One(select = "countQuestionByQuizId")),
    })
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdAndClassId(@Param("userId") int userId,@Param("classId")int classId);

    @Select("SELECT lq.id,lq.title,lqr.duration, lqr.record_date,lqr.score,lqr.status from lh_quiz lq\n" +
            "            inner join lh_quiz_record lqr on lq.id = lqr.quiz_id\n" +
            "            where ( (lqr.class_id=#{classId}) and (lqr.user_id=#{userId}) and (lq.status=true) " +
            "            and ( lqr.status = 0 or (lqr.status = 1 and lq.date_expire<now()) ) and lqr.is_delete_student = false and lq.draft = false) order by lqr.record_date desc\n"+
            "            limit #{paging.limit} offset #{paging.offset};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "quizTitle", column = "title"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "fullScore",  column = "id", one = @One(select = "countQuestionByQuizId")),
    })
    List<ClassroomHistoryStudent> filterAllStudentHistoryByUserIdAndClassIdByPage(@Param("userId") int userId,@Param("classId")int classId,@Param("paging")Paging paging);
}
